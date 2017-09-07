package uk.co.monotonic.object_allocation_rate;

import com.opencsv.CSVReader;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;
import java.util.Map.Entry;

public class HighAllocationStatisticsService extends HttpServlet
{
    public static void main(final String[] args) throws Exception
    {
        final ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(HighAllocationStatisticsService.class, "/");

        final Server server = new Server(9000);
        server.setHandler(servletHandler);
        server.dumpStdErr();
        server.start();
        server.join();
    }

    @Override
    protected void doPost(
        final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException
    {
        final ServletInputStream inputStream = req.getInputStream();
        final CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
        final Iterator<String[]> iterator = csvReader.iterator();
        final String[] headers = iterator.next();

        final Map<String, Double> totals = new HashMap<>();
        for (String header : headers)
        {
            totals.put(header, 0.0);
        }

        while (iterator.hasNext())
        {
            final Map<String, Double> values = new HashMap<>();
            final String[] row = iterator.next();
            for (int i = 0; i < headers.length; i++)
            {
                final String value = row[i];

                if (isNumeric(value))
                {
                    final double number = Double.parseDouble(value);
                    final String header = headers[i];
                    values.put(header, number);
                }
            }

            add(values, totals);
        }

        printResponse(resp, totals, csvReader.getLinesRead());
    }

    private void add(
        final Map<String, Double> values, final Map<String, Double> total)
    {
        for (Entry<String, Double> entry : total.entrySet())
        {
            final String headerName = entry.getKey();
            final Double value = values.get(headerName);
            if (value != null)
            {
                entry.setValue(value + entry.getValue());
            }
        }
    }

    private boolean isNumeric(final String cell)
    {
        if (cell.isEmpty())
        {
            return false;
        }

        for (int i = 0; i < cell.length(); i++)
        {
            final char c = cell.charAt(i);
            if (c != '.' && (c > '9' || c < '0'))
            {
                return false;
            }
        }

        return true;
    }

    private void printResponse(
        final HttpServletResponse resp,
        final Map<String, Double> totals,
        final long linesRead) throws IOException
    {
        resp.setContentType("text/plain");
        final Writer writer = new OutputStreamWriter(resp.getOutputStream());
        for (Entry<String, Double> entry : totals.entrySet())
        {
            writer.write(entry.getKey());
            writer.write(':');
            writer.write(String.valueOf(entry.getValue() / linesRead));
            writer.write('\n');
        }
        writer.close();
    }
}

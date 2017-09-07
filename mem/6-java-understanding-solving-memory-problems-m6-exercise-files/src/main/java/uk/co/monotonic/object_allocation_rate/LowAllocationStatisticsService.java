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
import java.util.Iterator;

public class LowAllocationStatisticsService extends HttpServlet
{
    public static void main(final String[] args) throws Exception
    {
        final ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(LowAllocationStatisticsService.class, "/");

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
        final double[] totals = new double[headers.length];

        while (iterator.hasNext())
        {
            final String[] row = iterator.next();
            for (int i = 0; i < headers.length; i++)
            {
                final String value = row[i];

                if (isNumeric(value))
                {
                    final double number = Double.parseDouble(value);
                    totals[i] += number;
                }
            }
        }

        printResponse(resp, headers, totals, csvReader.getLinesRead());
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
        final String[] headers,
        final double[] totals,
        final long linesRead) throws IOException
    {
        resp.setContentType("text/plain");
        final Writer writer = new OutputStreamWriter(resp.getOutputStream());
        for (int i = 0; i < headers.length; i++)
        {
            writer.write(headers[i]);
            writer.write(':');
            writer.write(String.valueOf(totals[i] / linesRead));
            writer.write('\n');
        }
        writer.close();
    }
}

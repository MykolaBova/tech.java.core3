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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MemoryHeavyStatisticsService extends HttpServlet
{

    private static final int SALES_VOLUME_COL = 9;

    public static void main(final String[] args) throws Exception
    {
        final ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(MemoryHeavyStatisticsService.class, "/");

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
        iterator.next();

        final List<Integer> salesVolumes = new ArrayList<>();
        while (iterator.hasNext())
        {
            final String[] row = iterator.next();
            final String salesVolumeCell = row[SALES_VOLUME_COL];
            final int salesVolume = salesVolumeCell.isEmpty() ? 0 : Integer.parseInt(salesVolumeCell);
            salesVolumes.add(salesVolume);
        }

        final double mean = mean(salesVolumes);
        printResponse(resp, mean);
    }

    private void printResponse(final HttpServletResponse resp, final double mean) throws IOException
    {
        resp.setContentType("text/plain");
        final Writer writer = new OutputStreamWriter(resp.getOutputStream());
        writer.write("Mean Sales Volume: ");
        writer.write(String.valueOf(mean));
        writer.write('\n');
        writer.close();
    }

    private double mean(final List<Integer> salesVolumes)
    {
        int total = 0;
        for (Integer volume : salesVolumes)
        {
            total += volume;
        }
        return ((double) total) / salesVolumes.size();
    }
}

package uk.co.monotonic.advanced_memory_leaks.off_heap;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class OffHeapLeak
{
    private static final int BUFFER_SIZE = 64 * 1024 * 1024;

    public static void main(String[] args) throws IOException
    {
        final List<ByteBuffer> buffers = new ArrayList<>();
        while (true)
        {
            buffers.add(ByteBuffer.allocateDirect(BUFFER_SIZE));

            System.out.println("Press any key to continue");
            System.in.read();
        }
    }
}

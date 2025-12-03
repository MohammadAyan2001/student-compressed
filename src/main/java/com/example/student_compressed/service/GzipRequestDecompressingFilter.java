package com.example.student_compressed.service;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.*;
import java.util.zip.GZIPInputStream;

@Component
public class GzipRequestDecompressingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(GzipRequestDecompressingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String encoding = request.getHeader("Content-Encoding");

        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
            long startTime = System.currentTimeMillis();

            // Read compressed bytes for logging size
            byte[] compressedBytes = request.getInputStream().readAllBytes();
            int compressedSize = compressedBytes.length;

            byte[] decompressedBytes = decompress(new ByteArrayInputStream(compressedBytes));
            int decompressedSize = decompressedBytes.length;

            logger.info("GZIP Upload: compressed size={} bytes, decompressed size={} bytes", compressedSize, decompressedSize);

            HttpServletRequest wrapped = new HttpServletRequestWrapper(request) {
                @Override
                public ServletInputStream getInputStream() {
                    ByteArrayInputStream byteStream = new ByteArrayInputStream(decompressedBytes);

                    return new ServletInputStream() {
                        @Override
                        public int read() {
                            return byteStream.read();
                        }

                        @Override
                        public boolean isFinished() {
                            return byteStream.available() == 0;
                        }

                        @Override
                        public boolean isReady() {
                            return true;
                        }

                        @Override
                        public void setReadListener(ReadListener listener) { }
                    };
                }

                @Override
                public int getContentLength() {
                    return decompressedBytes.length;
                }

                @Override
                public long getContentLengthLong() {
                    return decompressedBytes.length;
                }
            };

            long endTime = System.currentTimeMillis();
            logger.info("Decompression took {} ms", (endTime - startTime));

            chain.doFilter(wrapped, response);
            return;
        }

        chain.doFilter(request, response);
    }

    private byte[] decompress(InputStream inputStream) throws IOException {
        try (GZIPInputStream gis = new GZIPInputStream(inputStream);
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            byte[] tmp = new byte[4096];
            int n;
            while ((n = gis.read(tmp)) > 0) {
                buffer.write(tmp, 0, n);
            }
            return buffer.toByteArray();
        }
    }
}

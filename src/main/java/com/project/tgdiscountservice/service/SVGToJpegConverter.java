package com.project.tgdiscountservice.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class SVGToJpegConverter {
    @SneakyThrows
    public void transcodeSVGToBufferedImage(String imageUrl) {
        //Step -1: We read the input SVG document into Transcoder Input
        //We use Java NIO for this purpose
        OutputStream pngOstream = null;
        try {
            InputStream inputStream = new URL(imageUrl).openStream();
            TranscoderInput inputSvgImage = new TranscoderInput(inputStream);
            //Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
            Path path = Paths.get("src/main/resources/pictures/pic.png");
            File file = path.toFile();
            pngOstream = new FileOutputStream(file);
            TranscoderOutput outputPngImage = new TranscoderOutput(pngOstream);
            // Step-3: Create PNGTranscoder and define hints if required
            PNGTranscoder myConverter = new PNGTranscoder();
            // Step-4: Convert and Write output
            myConverter.transcode(inputSvgImage, outputPngImage);
            // Step 5- close / flush Output Stream
            pngOstream.flush();
            pngOstream.close();
        } catch (TranscoderException | IOException e) {
            e.printStackTrace();
        } finally {
            pngOstream.flush();
            pngOstream.close();
        }
    }
}

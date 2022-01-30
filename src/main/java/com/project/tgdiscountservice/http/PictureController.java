package com.project.tgdiscountservice.http;

import com.project.tgdiscountservice.service.SVGToJpegConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("pictures")
public class PictureController {

    private final SVGToJpegConverter svgToJpegConverter;

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getPicture(String imageUrl) throws IOException {
      log.info("PictureController getPicture - {}", imageUrl);
        svgToJpegConverter.transcodeSVGToBufferedImage(imageUrl);
        return Files.readAllBytes(Paths.get("src/main/resources/pictures/pic.png"));
    }

}

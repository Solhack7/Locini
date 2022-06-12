package com.springsecurity.ws.Service;

import com.springsecurity.ws.Entity.ImageEntity;
import com.springsecurity.ws.Exception.ImageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    ImageEntity store( MultipartFile file) throws IOException;
    byte[] downloadImage(String idClientPhoto);
    List<ImageEntity> getAllImages();
    ImageEntity checkExixtImg(String idBrowserImg) throws ImageException;
}

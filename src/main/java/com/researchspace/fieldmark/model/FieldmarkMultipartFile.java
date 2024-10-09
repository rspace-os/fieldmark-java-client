package com.researchspace.fieldmark.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public class FieldmarkMultipartFile implements MultipartFile {

  private final byte[] input;
  private final String fileName;

  public FieldmarkMultipartFile(@NonNull byte[] input, String fileName) {
    this.input = input;
    this.fileName = fileName;
  }

  @Override
  public String getName() {
    return this.fileName;
  }

  @Override
  public String getOriginalFilename() {
    return this.fileName;
  }

  @Override
  public String getContentType() {
    return "application/octet-stream";
  }

  @Override
  public boolean isEmpty() {
    return input == null || input.length == 0;
  }

  @Override
  public long getSize() {
    return input.length;
  }

  @Override
  public byte[] getBytes() throws IOException {
    return input;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return new ByteArrayInputStream(input);
  }

  @Override
  public void transferTo(File destination) throws IOException, IllegalStateException {
    try (FileOutputStream fos = new FileOutputStream(destination)) {
      fos.write(input);
    }
  }


}

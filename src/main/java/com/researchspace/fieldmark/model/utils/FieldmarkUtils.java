package com.researchspace.fieldmark.model.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.io.FileUtils;

public class FieldmarkUtils {

  private FieldmarkUtils(){}

  public static Map<String, byte[]> createFilesMap(String notebookId, byte[] zipFileBytes) throws IOException {
    Map<String, byte[]> result = new LinkedHashMap<>();
    File fileFromBytes = null;
    try {
      Path zipDirectory = Files.createTempDirectory(notebookId + "_");
      fileFromBytes = new File(zipDirectory + "/FieldmarkFile.zip");
      FileUtils.writeByteArrayToFile(fileFromBytes, zipFileBytes);

      try (ZipFile zipFile = new ZipFile(fileFromBytes)) {
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements()) {
          ZipEntry entry = entries.nextElement();
          if (!entry.isDirectory()) {
            result.put(entry.getName(), zipFile.getInputStream(entry).readAllBytes());
          }
        }
      }
    } finally {
      if (fileFromBytes != null) {
        FileUtils.delete(fileFromBytes);
      }
    }
    return result;
  }

}

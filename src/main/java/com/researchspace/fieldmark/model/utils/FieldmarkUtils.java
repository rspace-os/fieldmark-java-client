package com.researchspace.fieldmark.model.utils;

import com.researchspace.fieldmark.model.FieldmarkFieldDetail;
import com.researchspace.fieldmark.model.FieldmarkNotebook;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
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

  public static Map<String, String> buildFieldTypeMap(FieldmarkNotebook fieldmarkNotebook) {
    Map<String, String> recordFieldTypes = new HashMap<>();
    for (Entry<String, FieldmarkFieldDetail> typeByFieldName :
        fieldmarkNotebook.getUiSpecification().getFields().entrySet()) {
      recordFieldTypes.put(typeByFieldName.getKey(), typeByFieldName.getValue().getFieldType());
    }
    return recordFieldTypes;
  }

}

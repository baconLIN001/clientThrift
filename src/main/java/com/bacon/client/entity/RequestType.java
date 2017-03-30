/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.bacon.client.entity;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum RequestType implements TEnum {
  OFFLINE_DATA_UPLOAD(0),
  DATABASE_DATA_UPLOAD(1),
  FILELIST_VIEW(2),
  OFFline_File_PREIVEW(3),
  GET_LIVE_CLIENTS(4);

  private final int value;

  private RequestType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static RequestType findByValue(int value) { 
    switch (value) {
      case 0:
        return OFFLINE_DATA_UPLOAD;
      case 1:
        return DATABASE_DATA_UPLOAD;
      case 2:
        return FILELIST_VIEW;
      case 3:
        return OFFline_File_PREIVEW;
      case 4:
        return GET_LIVE_CLIENTS;
      default:
        return null;
    }
  }
}

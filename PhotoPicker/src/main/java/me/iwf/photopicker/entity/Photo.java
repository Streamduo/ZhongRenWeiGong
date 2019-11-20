package me.iwf.photopicker.entity;

/**
 * Created by donglua on 15/6/30.
 */
public class Photo {

  private int id;
  private String path;
  private int uploadIndex; //上传图片数字顺序


  public Photo(int id, String path) {
    this.id = id;
    this.path = path;
  }

  public Photo() {
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Photo)) return false;

    Photo photo = (Photo) o;

    return id == photo.id;
  }

  @Override public int hashCode() {
    return id;
  }

  public int getUploadIndex() {
    return uploadIndex;
  }

  public void setUploadIndex(int uploadIndex) {
    this.uploadIndex = uploadIndex;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}

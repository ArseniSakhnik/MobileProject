package ServerService.Models;

import com.google.gson.annotations.SerializedName;

import java.nio.charset.StandardCharsets;

public class CouponCreator {
    @SerializedName("id")
    public int id;
    @SerializedName("targetX")
    public Double targetX;
    @SerializedName("radius")
    public Double targetY;
    @SerializedName("isActive")
    public boolean isActive;
    @SerializedName("description")
    private String description;

    @Override
    public String toString() {
        return "CouponCreator{" +
                "id=" + id +
                ", targetX=" + targetX +
                ", targetY=" + targetY +
                ", isActive=" + isActive +
                ", description='" + getDescription() + '\'' +
                '}';
    }

    public String getDescription() {
        return new String(description.getBytes(StandardCharsets.UTF_8));
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

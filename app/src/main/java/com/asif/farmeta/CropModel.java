package com.asif.farmeta;

public class CropModel {
    private String cropId;
    private String cropName;
    private String status;
    private String expectedYield;
    private String startDate;
    private String farmerId;
    private String farmerName;

    // Farmer mode constructor
    public CropModel(String cropId, String cropName, String status, String expectedYield, String startDate) {
        this.cropId = cropId;
        this.cropName = cropName;
        this.status = status;
        this.expectedYield = expectedYield;
        this.startDate = startDate;
    }

    // Buyer mode constructor
    public CropModel(String cropId, String cropName, String status, String expectedYield, String startDate, String farmerId, String farmerName) {
        this.cropId = cropId;
        this.cropName = cropName;
        this.status = status;
        this.expectedYield = expectedYield;
        this.startDate = startDate;
        this.farmerId = farmerId;
        this.farmerName = farmerName;
    }

    public String getCropId() { return cropId; }
    public String getCropName() { return cropName; }
    public String getStatus() { return status; }
    public String getExpectedYield() { return expectedYield; }
    public String getStartDate() { return startDate; }
    public String getFarmerId() { return farmerId; }
    public String getFarmerName() { return farmerName; }
}

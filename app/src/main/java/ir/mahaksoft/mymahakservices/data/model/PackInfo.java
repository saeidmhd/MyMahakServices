
package ir.mahaksoft.mymahakservices.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackInfo {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("PackageNo")
    @Expose
    private int packageNo;
    @SerializedName("AppId")
    @Expose
    private int appId;
    @SerializedName("AppName")
    @Expose
    private String appName;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("ProductType")
    @Expose
    private String productType;
    @SerializedName("PackageValidDate")
    @Expose
    private String packageValidDate;
    @SerializedName("AddOns")
    @Expose
    private String addOns;
    @SerializedName("Series")
    @Expose
    private String series;
    @SerializedName("EndTiketCount")
    @Expose
    private int endTiketCount;
    @SerializedName("EndTiketDate")
    @Expose
    private String endTiketDate;
    @SerializedName("LockNo")
    @Expose
    private String lockNo;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("Price")
    @Expose
    private int price;
    @SerializedName("YearCreditPrice")
    @Expose
    private int yearCreditPrice;
    @SerializedName("monthCreditPrice")
    @Expose
    private int monthCreditPrice;
    @SerializedName("TamdidLink")
    @Expose
    private String tamdidLink;
    @SerializedName("AgencyID")
    @Expose
    private String agencyID;
    @SerializedName("AgencyName")
    @Expose
    private String agencyName;
    @SerializedName("AgencyTell")
    @Expose
    private String agencyTell;
    @SerializedName("LockType")
    @Expose
    private int lockType;
    @SerializedName("LockTypeName")
    @Expose
    private String lockTypeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(int packageNo) {
        this.packageNo = packageNo;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getPackageValidDate() {
        return packageValidDate;
    }

    public void setPackageValidDate(String packageValidDate) {
        this.packageValidDate = packageValidDate;
    }

    public String getAddOns() {
        return addOns;
    }

    public void setAddOns(String addOns) {
        this.addOns = addOns;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getEndTiketCount() {
        return endTiketCount;
    }

    public void setEndTiketCount(int endTiketCount) {
        this.endTiketCount = endTiketCount;
    }

    public String getEndTiketDate() {
        return endTiketDate;
    }

    public void setEndTiketDate(String endTiketDate) {
        this.endTiketDate = endTiketDate;
    }

    public String getLockNo() {
        return lockNo;
    }

    public void setLockNo(String lockNo) {
        this.lockNo = lockNo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getYearCreditPrice() {
        return yearCreditPrice;
    }

    public void setYearCreditPrice(int yearCreditPrice) {
        this.yearCreditPrice = yearCreditPrice;
    }

    public int getMonthCreditPrice() {
        return monthCreditPrice;
    }

    public void setMonthCreditPrice(int monthCreditPrice) {
        this.monthCreditPrice = monthCreditPrice;
    }

    public String getTamdidLink() {
        return tamdidLink;
    }

    public void setTamdidLink(String tamdidLink) {
        this.tamdidLink = tamdidLink;
    }

    public String getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(String agencyID) {
        this.agencyID = agencyID;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyTell() {
        return agencyTell;
    }

    public void setAgencyTell(String agencyTell) {
        this.agencyTell = agencyTell;
    }

    public int getLockType() {
        return lockType;
    }

    public void setLockType(int lockType) {
        this.lockType = lockType;
    }

    public String getLockTypeName() {
        return lockTypeName;
    }

    public void setLockTypeName(String lockTypeName) {
        this.lockTypeName = lockTypeName;
    }

}

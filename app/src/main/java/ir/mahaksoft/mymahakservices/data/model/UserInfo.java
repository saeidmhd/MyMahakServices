
package ir.mahaksoft.mymahakservices.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("AddressID")
    @Expose
    private int addressID;
    @SerializedName("PostalCode")
    @Expose
    private String postalCode;
    @SerializedName("AddressDetail")
    @Expose
    private String addressDetail;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("PropertyNames")
    @Expose
    private Object propertyNames;
    @SerializedName("PropertyValuesString")
    @Expose
    private Object propertyValuesString;
    @SerializedName("PropertyValuesBinary")
    @Expose
    private Object propertyValuesBinary;
    @SerializedName("LastUpdatedDate")
    @Expose
    private Object lastUpdatedDate;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Tell")
    @Expose
    private String tell;
    @SerializedName("Fax")
    @Expose
    private Object fax;
    @SerializedName("NationalCode")
    @Expose
    private String nationalCode;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("MahakId")
    @Expose
    private Object mahakId;
    @SerializedName("BirthDate")
    @Expose
    private String birthDate;
    @SerializedName("WebSite")
    @Expose
    private Object webSite;
    @SerializedName("AboutMe")
    @Expose
    private Object aboutMe;
    @SerializedName("Sex")
    @Expose
    private Object sex;
    @SerializedName("Job")
    @Expose
    private Object job;
    @SerializedName("EducationStatus")
    @Expose
    private Object educationStatus;
    @SerializedName("EducationDegree")
    @Expose
    private Object educationDegree;
    @SerializedName("Course")
    @Expose
    private Object course;
    @SerializedName("Image")
    @Expose
    private Object image;
    @SerializedName("Desc")
    @Expose
    private Object desc;
    @SerializedName("CurrentCredit")
    @Expose
    private Object currentCredit;
    @SerializedName("DefaultAddressID")
    @Expose
    private int defaultAddressID;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("ModifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("CityID")
    @Expose
    private int cityID;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("ProvinceID")
    @Expose
    private int provinceID;
    @SerializedName("ProvinceName")
    @Expose
    private String provinceName;
    @SerializedName("RowVersion")
    @Expose
    private String rowVersion;
    @SerializedName("AgencyID")
    @Expose
    private Object agencyID;
    @SerializedName("AgencyName")
    @Expose
    private String agencyName;
    @SerializedName("AgencyTell")
    @Expose
    private String agencyTell;
    @SerializedName("RoleId")
    @Expose
    private String roleId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Avatar")
    @Expose
    private String avatar;

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getPropertyNames() {
        return propertyNames;
    }

    public void setPropertyNames(Object propertyNames) {
        this.propertyNames = propertyNames;
    }

    public Object getPropertyValuesString() {
        return propertyValuesString;
    }

    public void setPropertyValuesString(Object propertyValuesString) {
        this.propertyValuesString = propertyValuesString;
    }

    public Object getPropertyValuesBinary() {
        return propertyValuesBinary;
    }

    public void setPropertyValuesBinary(Object propertyValuesBinary) {
        this.propertyValuesBinary = propertyValuesBinary;
    }

    public Object getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Object lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public Object getFax() {
        return fax;
    }

    public void setFax(Object fax) {
        this.fax = fax;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getMahakId() {
        return mahakId;
    }

    public void setMahakId(Object mahakId) {
        this.mahakId = mahakId;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Object getWebSite() {
        return webSite;
    }

    public void setWebSite(Object webSite) {
        this.webSite = webSite;
    }

    public Object getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(Object aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Object getSex() {
        return sex;
    }

    public void setSex(Object sex) {
        this.sex = sex;
    }

    public Object getJob() {
        return job;
    }

    public void setJob(Object job) {
        this.job = job;
    }

    public Object getEducationStatus() {
        return educationStatus;
    }

    public void setEducationStatus(Object educationStatus) {
        this.educationStatus = educationStatus;
    }

    public Object getEducationDegree() {
        return educationDegree;
    }

    public void setEducationDegree(Object educationDegree) {
        this.educationDegree = educationDegree;
    }

    public Object getCourse() {
        return course;
    }

    public void setCourse(Object course) {
        this.course = course;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Object getDesc() {
        return desc;
    }

    public void setDesc(Object desc) {
        this.desc = desc;
    }

    public Object getCurrentCredit() {
        return currentCredit;
    }

    public void setCurrentCredit(Object currentCredit) {
        this.currentCredit = currentCredit;
    }

    public int getDefaultAddressID() {
        return defaultAddressID;
    }

    public void setDefaultAddressID(int defaultAddressID) {
        this.defaultAddressID = defaultAddressID;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(String rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Object getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(Object agencyID) {
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}

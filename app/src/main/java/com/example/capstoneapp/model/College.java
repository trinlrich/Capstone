package com.example.capstoneapp.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.text.NumberFormat;
import java.util.Locale;

@ParseClassName("Colleges")
public class College extends ParseObject {

    public static final String KEY_ID = "schoolId";
    public static final String KEY_NAME = "schoolName";
    public static final String KEY_THUMBNAIL = "schoolThumbnail";
    public static final String KEY_CITY = "schoolCity";
    public static final String KEY_STATE = "schoolStateCode";
    public static final String KEY_ZIP_CODE = "schoolZipCode";
    public static final String KEY_LONG = "schoolLongitude";
    public static final String KEY_LAT = "schoolLatitude";
    public static final String KEY_COLLEGE_TYPE = "schoolControl";
    public static final String KEY_DEGREE_TYPE = "schoolDegreeType";
    public static final String KEY_MISSION = "schoolSpecialMission";
    public static final String KEY_ACCEPT_RATE = "schoolAcceptanceRate";
    public static final String KEY_UNDERGRAD_ENROLL = "schoolUndergradEnrollment";
    public static final String KEY_WEBPAGE = "schoolWebPage";
    public static final String KEY_COMP_RATE = "schoolCompletionRate100";
    public static final String KEY_AVG_SAT = "schoolAverageSATScore";
    public static final String KEY_ACT_75 = "schoolAct75Percentile";
    public static final String KEY_ACT_25 = "schoolAct25Percentile";
    public static final String KEY_AVG_COST_PUBLIC = "schoolAvgCostPublic";
    public static final String KEY_AVG_COST_PRIVATE = "schoolAvgCostPrivate";
    public static final String KEY_TUITION_IN = "schoolTutionFeeInState";
    public static final String KEY_TUITION_OUT = "schoolTutionFeeOutOfState";
    public static final String KEY_FED_LOAN_PERCENT = "schoolFederalLoanGrantPercentage";
    public static final String KEY_PELL_PERCENT = "schoolPellGrantPercentage";
    public static final String KEY_MED_STUDENT_DEBT = "schoolDebtMedian";
    public static final String KEY_MED_PARENT_DEBT = "schoolMedianParentPlusDebt";

    public static final String PUBLIC = "Public";
    public static final String PRIVATE_NP = "Private Nonprofit";
    public static final String PRIVATE_FP = "Private For-Profit";

    public static final String NOT_APPLICABLE = "Not Applicable";
    public static final String DATA_NOT_AVAILABLE = "Data Not Available";
    public static final int NO_DATA_NOT_ZERO = -9999;
    public static final int NO_DATA_ZERO = 0;

    private boolean isFavorite;
    public boolean isFavorite() {
        return isFavorite;
    }
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getCollegeId() { return getInt(KEY_ID); }
    public void setCollegeId(int id) { put(KEY_ID, id); }

    public String getName() { return getString(KEY_NAME); }
    public void setName(String name) { put(KEY_NAME, name); }

    public String getThumbnail() { return getString(KEY_THUMBNAIL); }
    public void setThumbnail(String thumbnail) { put(KEY_THUMBNAIL, thumbnail); }

    public String getCity() { return getString(KEY_CITY); }
    public void setCity(String city) { put(KEY_CITY, city); }

    public String getCollegeStateCode() { return getString(KEY_STATE); }
    public String getFullCollegeState() { return processStateCode(getCollegeStateCode()); }
    public void setCollegeState(String state) { put(KEY_STATE, state); }

    public String getZipCode() { return getString(KEY_ZIP_CODE); }
    public void setZipCode(String zipCode) { put(KEY_ZIP_CODE, zipCode); }

    public int getLong() { return getInt(KEY_LONG); }
    public void setLong(int longitude) { put(KEY_LONG, longitude); }

    public int getLat() { return getInt(KEY_LAT); }
    public void setLat(String latitude) { put(KEY_LAT, latitude); }

    public int getRawCollegeTypeData() { return getInt(KEY_COLLEGE_TYPE); }
    public String getCollegeTypeAsText() { return processCollegeTypeCode(getRawCollegeTypeData()); }
    public void setCollegeType(int collegeType) { put(KEY_COLLEGE_TYPE, collegeType); }

    public String getDegreeType() { return processDegreeType(getString(KEY_DEGREE_TYPE)); }
    public void setDegreeType(String degreeType) { put(KEY_DEGREE_TYPE, degreeType); }

    public String getMission() { return getString(KEY_MISSION); }
    public void setMission(String mission) { put(KEY_MISSION, mission); }

    public int getRawAcceptRateData() { return getInt(KEY_ACCEPT_RATE); }
    public String getAcceptRateAsText() { return processAcceptRate(getRawAcceptRateData()); }
    public void setAcceptRate(int acceptRate) { put(KEY_ACCEPT_RATE, acceptRate); }

    public String getUndergradEnroll() { return getString(KEY_UNDERGRAD_ENROLL); }
    public void setUndergradEnroll(String undergradEnroll) { put(KEY_UNDERGRAD_ENROLL, undergradEnroll); }

    public String getWebpage() { return getString(KEY_WEBPAGE); }
    public void setWebpage(String webpage) { put(KEY_WEBPAGE, webpage); }

    public String getRawCompRateData() { return getString(KEY_COMP_RATE); }
    public String getCompRateAsText() { return processCompRate(getRawCompRateData()); }
    public void setCompRate(String compRate) { put(KEY_COMP_RATE, compRate); }

    public String getAvgSat() { return getString(KEY_AVG_SAT); }
    public void setAvgSat(String avgSat) { put(KEY_AVG_SAT, avgSat); }

    public String getAct75() { return getString(KEY_ACT_75); }
    public void setAct75(String act75) { put(KEY_ACT_75, act75); }

    public String getAct25() { return getString(KEY_ACT_25); }
    public void setAct25(String act25) { put(KEY_ACT_25, act25); }

    public int getRawAvgCostPublicData() { return getInt(KEY_AVG_COST_PUBLIC); }
    public String getAvgCostPublicAsText() { return processAvgCost(getRawAvgCostPublicData()); }
    public void setAvgCostPublic(int avgCostPublic) { put(KEY_AVG_COST_PUBLIC, avgCostPublic); }

    public int getRawAvgCostPrivateData() { return getInt(KEY_AVG_COST_PUBLIC); }
    public String getAvgCostPrivateAsText() { return processAvgCost(getRawAvgCostPrivateData()); }
    public void setAvgCostPrivate(int avgCostPrivate) { put(KEY_AVG_COST_PRIVATE, avgCostPrivate); }

    public int getRawTuitionInData() { return getInt(KEY_TUITION_IN); }
    public String getTuitionInAsText() { return processTuition(getRawTuitionInData()); }
    public void setTuitionIn(int tuitionIn) { put(KEY_TUITION_IN, tuitionIn); }

    public int getRawTuitionOutData() { return getInt(KEY_TUITION_OUT); }
    public String getTuitionOutAsText() { return processTuition(getRawTuitionOutData()); }
    public void setTuitionOut(int tuitionOut) { put(KEY_TUITION_OUT, tuitionOut); }

    public int getRawFedLoadPercentData() { return getInt(KEY_FED_LOAN_PERCENT); }
    public String getFedLoanPercentAsText() { return processFinAidPercent(getRawFedLoadPercentData()); }
    public void setFedLoanPercent(int fedLoanPercent) { put(KEY_FED_LOAN_PERCENT, fedLoanPercent); }

    public int getRawPellPercentData() { return getInt(KEY_PELL_PERCENT); }
    public String getPellPercentAsText() { return  processFinAidPercent(getRawPellPercentData()); }
    public void setPellPercent(int pellPercent) { put(KEY_PELL_PERCENT, pellPercent); }

    public int getRawMedStudentDebtData() { return getInt(KEY_MED_STUDENT_DEBT); }
    public String getMedStudentDebtAsText() { return processMedianDebt(getRawMedStudentDebtData()); }
    public void setMedStudentDebt(int medStudentDebt) { put(KEY_MED_STUDENT_DEBT, medStudentDebt); }

    public int getRawMedParentDebtData() { return getInt(KEY_MED_PARENT_DEBT); }
    public String getMedParentDebtAsText() { return processMedianDebt(getRawMedParentDebtData()); }
    public void setMedParentDebt(int medParentDebt) { put(KEY_MED_PARENT_DEBT, medParentDebt); }

    public String getLocation() { return getCity() + ", " + getCollegeStateCode(); }

    public String convertToDollar(int amount) {
        Locale usa = new Locale("en", "US");
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
        return dollarFormat.format(amount);
    }

    /*
     * KEY_STATE - schoolStateCode
     */
    private String processStateCode(String code) {
        // TODO :: Process state code CA -> California (for list view in filtering)
        return code;
    }

    /*
     * KEY_CONTROL - schoolControl
     */
    private String processCollegeTypeCode(int control) {
        if (control == 1) {
            return PUBLIC;
        } else if (control == 2) {
            return PRIVATE_NP;
        } else if (control == 3) {
            return PRIVATE_FP;
        } else {
            return "Data Not Available";
        }
    }

    /*
     * KEY_DEGREE_TYPE - schoolDegreeType
     */
    private String processDegreeType(String degreeType) {
        if (degreeType.equals("Not Applicable")) {
            return "Not Available";
        } else {
            return degreeType;
        }
    }

    /*
     * KEY_ACCEPT_RATE - schoolAcceptanceRate
     */
    private String processAcceptRate(int acceptRate) {
        if (acceptRate == NO_DATA_ZERO) {
            return "Not Available";
        } else {
            return String.valueOf(acceptRate * 100);
        }
    }

    private String processCompRate(String compRate) {
        if (compRate.equals("Data Not Available")) {
            return "Data Not Available";
        } else {
            return String.valueOf(Float.valueOf(compRate) * 100) + '%';
        }
    }

    /*
     * KEY_AVG_COST_PUBLIC - schoolAvgCostPublic
     * KEY_AVG_COST_PRIVATE - schoolAvgCostPrivate
     */
    private String processAvgCost(int cost) {
        if (cost == NO_DATA_ZERO) {
            return "Data Not Available";
        } else {
            return String.valueOf(convertToDollar(cost));
        }
    }

    /*
     * KEY_TUITION_IN - schoolTutionFeeInState
     * KEY_TUITION_OUT - schoolTutionFeeOutOfState
     */
    private String processTuition(int tuition) {
        if (tuition == NO_DATA_NOT_ZERO) {
            return "Data Not Available";
        } else {
            return String.valueOf(convertToDollar(tuition));
        }
    }

    /*
     * KEY_FED_LOAN_PERCENT - schoolFederalLoanGrantPercentage
     * KEY_PELL_PERCENT - schoolPellGrantPercentage
     */
    private String processFinAidPercent(int percent) {
        if (percent == NO_DATA_ZERO) {
            return "Data Not Available";
        } else {
            return String.valueOf(percent * 100);
        }
    }

    /*
     * KEY_MED_STUDENT_DEBT - schoolDebtMedian
     * KEY_MED_PARENT_DEBT - schoolMedianParentPlusDebt
     */
    private String processMedianDebt(int debt) {
        if (debt == NO_DATA_NOT_ZERO) {
            return "Data Not Available";
        } else {
            return String.valueOf(convertToDollar(debt));
        }
    }

}

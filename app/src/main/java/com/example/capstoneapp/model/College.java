package com.example.capstoneapp.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    public static final String KEY_GRAD_RATE = "schoolCompletionRate100";
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

    public static final HashMap<String, String> STATES = new HashMap<>();
    public static final HashMap<Integer, String> COLLEGE_TYPES = new HashMap<>();
    public static final HashMap<String, String> MISSIONS = new HashMap<>();

    public static final String NOT_APPLICABLE = "Not Applicable";
    public static final String DATA_NOT_AVAILABLE = "Data Not Available";
    public static final int NO_DATA_NOT_ZERO = -9999;
    public static final int NO_DATA_ZERO = 0;

    public College() {
        createStatesMap();
        createCollegeTypesMap();
        createMissionsMap();
    }

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
    public static HashMap<String, String> getStates() { return STATES; }

    public String getZipCode() { return getString(KEY_ZIP_CODE); }
    public void setZipCode(String zipCode) { put(KEY_ZIP_CODE, zipCode); }

    public int getLong() { return getInt(KEY_LONG); }
    public void setLong(int longitude) { put(KEY_LONG, longitude); }

    public int getLat() { return getInt(KEY_LAT); }
    public void setLat(String latitude) { put(KEY_LAT, latitude); }

    public int getRawCollegeTypeData() { return getInt(KEY_COLLEGE_TYPE); }
    public String getCollegeTypeAsText() { return processCollegeTypeCode(getRawCollegeTypeData()); }
    public void setCollegeType(int collegeType) { put(KEY_COLLEGE_TYPE, collegeType); }
    public static HashMap<Integer, String> getCollegeTypes() { return COLLEGE_TYPES; }

    public String getDegreeType() { return processDegreeType(getString(KEY_DEGREE_TYPE)); }
    public void setDegreeType(String degreeType) { put(KEY_DEGREE_TYPE, degreeType); }

    public String getRawMissionData() { return getString(KEY_MISSION); }
    public String getFullMission() { return processMission(getRawMissionData()); }
    public void setMission(String mission) { put(KEY_MISSION, mission); }
    public static HashMap<String, String> getMissions() { return MISSIONS; }

    public int getRawAcceptRateData() { return getInt(KEY_ACCEPT_RATE); }
    public String getAcceptRateAsText() { return processAcceptRate(getRawAcceptRateData()); }
    public void setAcceptRate(int acceptRate) { put(KEY_ACCEPT_RATE, acceptRate); }

    public String getRawUndergradEnrollData() { return getString(KEY_UNDERGRAD_ENROLL); }
    public String getUndergradEnrollAsText() { return processUndergradEnroll(getRawUndergradEnrollData()); }
    public void setUndergradEnroll(String undergradEnroll) { put(KEY_UNDERGRAD_ENROLL, undergradEnroll); }

    public String getWebpage() { return getString(KEY_WEBPAGE); }
    public void setWebpage(String webpage) { put(KEY_WEBPAGE, webpage); }

    public String getRawGradRateData() { return getString(KEY_GRAD_RATE); }
    public String getGradRateAsText() { return processGradRate(getRawGradRateData()); }
    public void setGradRate(String gradRate) { put(KEY_GRAD_RATE, gradRate); }

    public String getAvgSat() { return getString(KEY_AVG_SAT); }
    public void setAvgSat(String avgSat) { put(KEY_AVG_SAT, avgSat); }

    public String getAct75() { return getString(KEY_ACT_75); }
    public void setAct75(String act75) { put(KEY_ACT_75, act75); }

    public String getAct25() { return getString(KEY_ACT_25); }
    public void setAct25(String act25) { put(KEY_ACT_25, act25); }

    public int getRawAvgCostPublicData() { return getInt(KEY_AVG_COST_PUBLIC); }
    public String getAvgCostPublicAsText() { return processAvgCost(getRawAvgCostPublicData()); }
    public void setAvgCostPublic(int avgCostPublic) { put(KEY_AVG_COST_PUBLIC, avgCostPublic); }

    public int getRawAvgCostPrivateData() { return getInt(KEY_AVG_COST_PRIVATE); }
    public String getAvgCostPrivateAsText() { return processAvgCost(getRawAvgCostPrivateData()); }
    public void setAvgCostPrivate(int avgCostPrivate) { put(KEY_AVG_COST_PRIVATE, avgCostPrivate); }

    public int getRawTuitionInData() { return getInt(KEY_TUITION_IN); }
    public String getTuitionInAsText() { return processTuition(getRawTuitionInData()); }
    public void setTuitionIn(int tuitionIn) { put(KEY_TUITION_IN, tuitionIn); }

    public int getRawTuitionOutData() { return getInt(KEY_TUITION_OUT); }
    public String getTuitionOutAsText() { return processTuition(getRawTuitionOutData()); }
    public void setTuitionOut(int tuitionOut) { put(KEY_TUITION_OUT, tuitionOut); }

    public int getRawFedLoanPercentData() { return getInt(KEY_FED_LOAN_PERCENT); }
    public String getFedLoanPercentAsText() { return processFinAidPercent(getRawFedLoanPercentData()); }
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

    public String getAvgCostAsText() {
        if (getRawAvgCostPublicData() != NO_DATA_ZERO) {
            return getAvgCostPublicAsText();
        }
        if (getRawAvgCostPrivateData() != NO_DATA_ZERO) {
            return getAvgCostPrivateAsText();
        }
        return DATA_NOT_AVAILABLE;
    }

    public String convertToDollar(int amount) {
        Locale usa = new Locale("en", "US");
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
        return dollarFormat.format(amount);
    }

    public void createStatesMap() {
        STATES.put("AL", "Alabama");
        STATES.put("AK", "Alaska");
        STATES.put("AZ", "Arizona");
        STATES.put("AR", "Arkansas");
        STATES.put("CA", "California");
        STATES.put("CO", "Colorado");
        STATES.put("CT", "Connecticut");
        STATES.put("DE", "Delaware");
        STATES.put("DC", "District Of Columbia");
        STATES.put("FL", "Florida");
        STATES.put("GA", "Georgia");
        STATES.put("HI", "Hawaii");
        STATES.put("ID", "Idaho");
        STATES.put("IL", "Illinois");
        STATES.put("IN", "Indiana");
        STATES.put("IA", "Iowa");
        STATES.put("KS", "Kansas");
        STATES.put("KY", "Kentucky");
        STATES.put("LA", "Louisiana");
        STATES.put("ME", "Maine");
        STATES.put("MD", "Maryland");
        STATES.put("MA", "Massachusetts");
        STATES.put("MI", "Michigan");
        STATES.put("MN", "Minnesota");
        STATES.put("MS", "Mississippi");
        STATES.put("MO", "Missouri");
        STATES.put("MT", "Montana");
        STATES.put("NE", "Nebraska");
        STATES.put("NV", "Nevada");
        STATES.put("NH", "New Hampshire");
        STATES.put("NJ", "New Jersey");
        STATES.put("NM", "New Mexico");
        STATES.put("NY", "New York");
        STATES.put("NC", "North Carolina");
        STATES.put("ND", "North Dakota");
        STATES.put("OH", "Ohio");
        STATES.put("OK", "Oklahoma");
        STATES.put("OR", "Oregon");
        STATES.put("PA", "Pennsylvania");
        STATES.put("RI", "Rhode Island");
        STATES.put("SC", "South Carolina");
        STATES.put("SD", "South Dakota");
        STATES.put("TN", "Tennessee");
        STATES.put("TX", "Texas");
        STATES.put("UT", "Utah");
        STATES.put("VT", "Vermont");
        STATES.put("VA", "Virginia");
        STATES.put("WA", "Washington");
        STATES.put("WV", "West Virginia");
        STATES.put("WI", "Wisconsin");
        STATES.put("WY", "Wyoming");
    }

    public void createCollegeTypesMap() {
        COLLEGE_TYPES.put(1, "Public");
        COLLEGE_TYPES.put(2, "Private Nonprofit");
        COLLEGE_TYPES.put(3, "Private For-Profit");
    }

    public void createMissionsMap() {
        MISSIONS.put("HBCU", "Historically Black Colleges and Universities, ");
        MISSIONS.put("PBI", "Predominantly Black Institution, ");
        MISSIONS.put("ANNHI", "Alaska Native / Native Hawaiian-serving Institutions, ");
        MISSIONS.put("TRIABL", "Tribal Colleges and Universities, ");
        MISSIONS.put("AANAPII", "Asian American / Native American-Pacific Islander-serving Institutions, ");
        MISSIONS.put("HSI", "Hispanic-serving institution, ");
        MISSIONS.put("NANTI", "Native American Non-Tribal Institutions, ");
        MISSIONS.put("MENONLY", "Men's Colleges and Universities, ");
        MISSIONS.put("WOMENONLY", "Women's Colleges and Universities, ");
    }

    /*
     * KEY_STATE - schoolStateCode
     */
    private String processStateCode(String code) {
        return STATES.getOrDefault(code, DATA_NOT_AVAILABLE);
    }

    /*
     * KEY_CONTROL - schoolControl
     */
    private String processCollegeTypeCode(int control) {
        return COLLEGE_TYPES.getOrDefault(control, DATA_NOT_AVAILABLE);
    }

    /*
     * KEY_DEGREE_TYPE - schoolDegreeType
     */
    private String processDegreeType(String degreeType) {
        if (degreeType.equals(NOT_APPLICABLE)) {
            return DATA_NOT_AVAILABLE;
        } else {
            return degreeType;
        }
    }

    private String processMission(String missions) {
        List<String> missionsList = Arrays.asList(missions.split(","));
        StringBuilder fullMissions = new StringBuilder();
        for (String mission : missionsList) {
            if (mission.equals(DATA_NOT_AVAILABLE))
                return DATA_NOT_AVAILABLE;
            else
                fullMissions.append(MISSIONS.get(mission) + ", ");
        }
        return fullMissions.substring(0, fullMissions.length() - 2);
    }

    /*
     * KEY_ACCEPT_RATE - schoolAcceptanceRate
     */
    private String processAcceptRate(int acceptRate) {
        if (acceptRate == NO_DATA_ZERO) {
            return DATA_NOT_AVAILABLE;
        } else {
            return String.valueOf(acceptRate * 100);
        }
    }

    private String processUndergradEnroll(String enrollment) {
        if (enrollment.equals(DATA_NOT_AVAILABLE)) {
            return DATA_NOT_AVAILABLE;
        } else {
            return NumberFormat.getNumberInstance(Locale.US).format(Integer.valueOf(enrollment));
        }
    }

    private String processGradRate(String compRate) {
        if (compRate.equals(DATA_NOT_AVAILABLE)) {
            return compRate;
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
            return DATA_NOT_AVAILABLE;
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
            return DATA_NOT_AVAILABLE;
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
            return DATA_NOT_AVAILABLE;
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
            return DATA_NOT_AVAILABLE;
        } else {
            return String.valueOf(convertToDollar(debt));
        }
    }
}

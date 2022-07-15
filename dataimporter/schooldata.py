import requests
import openpyxl

# Parse API Endpoint- https://parseapi.back4app.com
# Application Id - <your app id>
# Rest API key ; <your rest api key>
# Table -> https://parseapi.back4app.com/classes/Colleges


api_url = "https://parseapi.back4app.com/classes/Colleges"
PARSE_APPLICATION_ID = "<your app id>"
PARSE_REST_API_KEY = "<your rest api key>"

bing_subscription_key = "<your bing key>"
bing_search_url = "https://api.bing.microsoft.com/v7.0/images/search"

# Full path of the XLSX file where the data is stored. Please make sure that the XLS file has non-empty rows and
# non-empty cols at the start
path = "/Users/<username>/Documents/myschoolapp/MostRecentCohortsInstitution.xlsx"
SCHOOL_ID_INDEX = 0
SCHOOL_NAME_INDEX = 3
SCHOOL_NAME_CITY = 4
SCHOOL_STATE_CODE = 5
SCHOOL_ADDRESS_ZIPCODE = 6
SCHOOL_HOMEPAGE_INDEX = 8

# CONTROL ->
# 1	Public
# 2	Private nonprofit
# 3	Private for-profit
SCHOOL_OWNERSHIP_CONTROL = 16

# Location information
SCHOOL_LOCATION_LAT = 21
SCHOOL_LOCATION_LONG = 22

# size and setting classification
SCHOOL_CARNEGIE_NUMBER = 25

# HBCU -> Flag for Historically Black College and University
SCHOOL_SPECIAL_MISSION_HBCU = 26

# PBI -> Flag for predominantly black institution
SCHOOL_SPECIAL_MISSION_PBI = 27

# ANNHI -> Flag for Alaska Native Native Hawaiian serving institution
SCHOOL_SPECIAL_MISSION_ANNHI = 28

# TRIBAL -> Flag for tribal college and university
SCHOOL_SPECIAL_MISSION_TRIBAL = 29

# AANAPII -> Flag for Asian American Native American Pacific Islander-serving institution
SCHOOL_SPECIAL_MISSION_AANAPII = 30

# HSI -> Flag for Hispanic-serving institution
SCHOOL_SPECIAL_MISSION_HSI = 31

# NANTI -> Flag for Native American non-tribal institution
SCHOOL_SPECIAL_MISSION_NANTI = 32

# MENONLY -> Flag for men-only college
SCHOOL_SPECIAL_MISSION_MENONLY = 33

# WOMENONLY -> Flag for women-only college
SCHOOL_SPECIAL_MISSION_WOMENONLY = 34

# Acceptance Rate (NOTE: There is no GPA data available . Instead lets show Acceptance Rate in College List Items)
# this will be a float value and you need to multiply by 100 to get a percentage
SCHOOL_ACCEPTANCE_RATE = 36

# 25th percentile of the ACT cumulative score
SCHOOL_ACT_SCORE_25PERCENTILE = 47

# 75th percentile of the ACT cumulative score
SCHOOL_ACT_SCORE_75PERCENTILE = 48

# Average SAT Score
SCHOOL_AVG_SAT_SCORE = 59
# The number of degree/certificate-seeking undergraduates enrolled in the fall,
# as reported in the IPEDS Fall Enrollment component
SCHOOL_UNDERGRAD_ENROLLMENT = 290

# COST ->
# avg_net_price.public
# avg_net_price.private
SCHOOL_AVERAGE_COST_PUBLIC = 316
SCHOOL_AVERAGE_COST_PRIVATE = 317

# TUITIONFEE_IN -> In-state tuition and fees
SCHOOL_TUTION_FEE_INSTATE = 378

# TUITIONFEE_OUT -> Out-of-state tuition and fees
SCHOOL_TUTION_FEE_OUTOFSTATE = 379

# DEBT_MDN -> This is the median loan debt accumulated at the institution19 by all
# student borrowers of federal loan
SCHOOL_DEBT_MEDIAN = 1503

# Completion rate for first-time, full-time students at four-year institutions (100% of expected time to completion)
SCHOOL_COMPLETION_RATE = 1730

# FTFTPCTPELL -> Percentage of full-time, first-time degree/certificate-seeking undergraduate students awarded a Pell
# Grant
SCHOOL_PELL_GRANT_PERCENT = 1977

# FTFTPCTFLOAN -> Percentage of full-time, first-time degree/certificate-seeking undergraduate students awarded a
# federal loan
SCHOOL_FEDERAL_GRANT_PERCENT = 1978

# PLUS_DEBT_INST_MD -> Median PLUS loan debt disbursed at this institution
SCHOOL_MEDIAN_PARENT_PLUS_DEBT = 1992


def process_excel(filename):
    wb_obj = openpyxl.load_workbook(filename)
    sheet_obj = wb_obj.active
    print(f"The title of the Worksheet is: {sheet_obj.title}")
    print(f"Cells that contain data: {sheet_obj.calculate_dimension()}")

    # Start with 2nd row as the first row is assumed to contain the description of the cells
    for value in sheet_obj.iter_rows(min_row=2, values_only=True):
        print(value)
        print(
            f"Data is-> {value[SCHOOL_ID_INDEX]}: {value[SCHOOL_NAME_INDEX]}: "
            f"{value[SCHOOL_NAME_CITY]}: {value[SCHOOL_ADDRESS_ZIPCODE]}: {value[SCHOOL_HOMEPAGE_INDEX]}: "
            f"{value[SCHOOL_LOCATION_LAT]}: {value[SCHOOL_LOCATION_LONG]}")
        # convert into JSON and send to parse database:
        schoolthumbnail = getimagefrombing(value[SCHOOL_NAME_INDEX])
        updateparsedatabase(value, schoolthumbnail)


def getimagefrombing(schoolname):
    schoolnamecampus = schoolname + " Campus"
    headers = {"Ocp-Apim-Subscription-Key": bing_subscription_key}
    params = {"q": schoolnamecampus, "license": "public", "imageType": "photo"}

    response = requests.get(bing_search_url, headers=headers, params=params)
    response.raise_for_status()
    search_results = response.json()
    thumbnail_urls = [img["thumbnailUrl"] for img in search_results["value"][:16]]
    if len(thumbnail_urls):
        print("-> Got Thumbnail :" + thumbnail_urls[0])
        return thumbnail_urls[0]
    else:
        print("NO THUMBNAIL")
        return ""


def updateparsedatabase(value, school_thumbnail):
    requestheaders = {
        "X-Parse-Application-Id": PARSE_APPLICATION_ID,
        "X-Parse-REST-API-Key": PARSE_REST_API_KEY,
        "Content-Type": "application/json"
    }
    # Preprocessing of any data or conversion of data before sending to parse

    zipcode = processzipcode(value[SCHOOL_ADDRESS_ZIPCODE])
    avgCostPublic = processaaveragecost(value[SCHOOL_AVERAGE_COST_PUBLIC])
    avgCostPrivate = processaaveragecost(value[SCHOOL_AVERAGE_COST_PRIVATE])
    schoolAcceptanceRate = processacceptancerate(value[SCHOOL_ACCEPTANCE_RATE])
    undergradEnrollMent = processundergradenrollment(value[SCHOOL_UNDERGRAD_ENROLLMENT])
    avgsatscore = processavgsatactscore(value[SCHOOL_AVG_SAT_SCORE])
    degreeType = processcarnegieclassification(value[SCHOOL_CARNEGIE_NUMBER])
    completionrate100 = processcompletionrate(value[SCHOOL_COMPLETION_RATE])
    avgactscore25percentile = processavgsatactscore(value[SCHOOL_ACT_SCORE_25PERCENTILE])
    avgactscore75percentile = processavgsatactscore(value[SCHOOL_ACT_SCORE_75PERCENTILE])
    specialmissionstring = processspecialmission(value)
    tutionFeeInstate = processtutionfee(value[SCHOOL_TUTION_FEE_INSTATE])
    tutionFeeOutOfstate = processtutionfee(value[SCHOOL_TUTION_FEE_OUTOFSTATE])
    federalLoanPercentage = processloangrant(value[SCHOOL_FEDERAL_GRANT_PERCENT])
    pellLoanPercentage = processloangrant(value[SCHOOL_PELL_GRANT_PERCENT])
    schoolDebtMedian = processdebtmedian(value[SCHOOL_DEBT_MEDIAN])
    schoolParentPlusDebt = processdebtmedian(value[SCHOOL_MEDIAN_PARENT_PLUS_DEBT])
    schoolLat = processlatlong(value[SCHOOL_LOCATION_LAT])
    schoolLong = processlatlong(value[SCHOOL_LOCATION_LONG])

    schooldata = {"schoolId": value[SCHOOL_ID_INDEX], "schoolName": value[SCHOOL_NAME_INDEX],
                  "schoolWebPage": value[SCHOOL_HOMEPAGE_INDEX],
                  "schoolCity": value[SCHOOL_NAME_CITY], "schoolZipCode": zipcode,
                  "schoolLatitude": schoolLat, "schoolLongitude": schoolLong,
                  "schoolControl": value[SCHOOL_OWNERSHIP_CONTROL], "schoolAvgCostPublic": avgCostPublic,
                  "schoolAvgCostPrivate": avgCostPrivate, "schoolAcceptanceRate": schoolAcceptanceRate,
                  "schoolStateCode": value[SCHOOL_STATE_CODE],
                  "schoolUndergradEnrollment": undergradEnrollMent,
                  "schoolAverageSATScore": avgsatscore,
                  "schoolDegreeType": degreeType,
                  "schoolCompletionRate100": completionrate100,
                  "schoolAct25Percentile": avgactscore25percentile,
                  "schoolAct75Percentile": avgactscore75percentile,
                  "schoolSpecialMission": specialmissionstring,
                  "schoolTutionFeeInState": tutionFeeInstate,
                  "schoolTutionFeeOutOfState": tutionFeeOutOfstate,
                  "schoolFederalLoanGrantPercentage": federalLoanPercentage,
                  "schoolPellGrantPercentage": pellLoanPercentage,
                  "schoolDebtMedian": schoolDebtMedian,
                  "schoolMedianParentPlusDebt": schoolParentPlusDebt,
                  "schoolThumbnail": school_thumbnail}
    response = requests.post(api_url, json=schooldata, headers=requestheaders)
    print(response.json())
    print(response.status_code)


def processzipcode(zipcode):
    # Convert Zip Code to String
    return str(zipcode)


def processacceptancerate(schoolacceptancerate):
    # Process values of Acceptance Rate. Some data is not available and they are null. Convert to float number.
    # In android app, if you get a 0 for acceptance then you can display 'data not available'.
    if schoolacceptancerate == "NULL":
        return 0
    else:
        return schoolacceptancerate


def processaaveragecost(averagecost):
    # Check values of Cost if they are null make them 0
    if averagecost == "NULL":
        return 0
    else:
        return averagecost


def processundergradenrollment(enrollment):
    # Check values of number of enrollment in fall
    if enrollment == "NULL":
        return "Data Not Available"
    else:
        return str(enrollment)


def processavgsatactscore(satactscore):
    # Check values of Cost if they are null make them 0
    if satactscore == "NULL":
        return "Data Not Available"
    else:
        return str(satactscore)


def processcarnegieclassification(classificationnumber):
    if classificationnumber == 0:
        return "Not Classified"
    elif classificationnumber == 1:
        return "Two-year, very small"
    elif classificationnumber == 2:
        return "Two-year, small"
    elif classificationnumber == 3:
        return "Two-year, medium"
    elif classificationnumber == 4:
        return "Two-year, large"
    elif classificationnumber == 5:
        return "Two-year, very large"
    elif classificationnumber == 6:
        return "Four-year, very small, primarily nonresidential"
    elif classificationnumber == 7:
        return "Four-year, very small, primarily residential"
    elif classificationnumber == 8:
        return "Four-year, very small, highly residential"
    elif classificationnumber == 9:
        return "Four-year, small, primarily nonresidential"
    elif classificationnumber == 10:
        return "Four-year, small, primarily residential"
    elif classificationnumber == 11:
        return "Four-year, small, highly residential"
    elif classificationnumber == 12:
        return "Four-year, medium, primarily nonresidential"
    elif classificationnumber == 13:
        return "Four-year, medium, primarily residential"
    elif classificationnumber == 14:
        return "Four-year, medium, highly residential"
    elif classificationnumber == 15:
        return "Four-year, large, primarily nonresidential"
    elif classificationnumber == 16:
        return "Four-year, large, primarily residential"
    elif classificationnumber == 17:
        return "Four-year, large, highly residential"
    elif classificationnumber == 18:
        return "Exclusively graduate/professional"
    else:
        return "Not Applicable"


def processcompletionrate(completionrate):
    # Completion rate for first-time, full-time students at four-year institutions (100% of expected time to completion)
    if completionrate == "NULL":
        return "Data Not Available"
    else:
        return str(completionrate)


def processloangrant(loangrantpercentage):
    # This would be percentage value in float for Loan Granted.
    # If you want to process it in Client side then 0 means "Data not available"
    if loangrantpercentage == "NULL":
        return 0
    else:
        return loangrantpercentage


def processspecialmission(value):
    result = ''
    if value[SCHOOL_SPECIAL_MISSION_HBCU] == 1:
        result += "HBCU,"
    if value[SCHOOL_SPECIAL_MISSION_PBI] == 1:
        result += "PBI,"
    if value[SCHOOL_SPECIAL_MISSION_ANNHI] == 1:
        result += "ANNHI,"
    if value[SCHOOL_SPECIAL_MISSION_TRIBAL] == 1:
        result += "TRIBAL,"
    if value[SCHOOL_SPECIAL_MISSION_AANAPII] == 1:
        result += "AANAPII,"
    if value[SCHOOL_SPECIAL_MISSION_HSI] == 1:
        result += "HSI,"
    if value[SCHOOL_SPECIAL_MISSION_NANTI] == 1:
        result += "NANTI,"
    if value[SCHOOL_SPECIAL_MISSION_MENONLY] == 1:
        result += "MENONLY,"
    if value[SCHOOL_SPECIAL_MISSION_WOMENONLY] == 1:
        result += "WOMENONLY,"

    if len(result) == 0:
        return "Data Not Available"
    else:
        return result


def processtutionfee(fee):
    # Return the fee as Integer/Float. For NULL Values use some invalid number so that it can be processed by Client
    # side Android code
    if fee == "NULL":
        return -9999
    else:
        return fee


def processdebtmedian(debtvalue):
    # check for invalid values and if not available return -9999 to be processed by cleint
    if (debtvalue == "NULL") or (debtvalue == "PrivacySuppressed"):
        return -9999
    else:
        return debtvalue


def processlatlong(latlong):
    # check for invalid vlat/long values -9999
    if latlong == "NULL":
        return -9999
    else:
        return latlong


process_excel(path)

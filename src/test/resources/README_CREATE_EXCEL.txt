Create an Excel file named "testdata.xlsx" in this folder with the following structure:

Sheet Name: "LoginData"

| email              | password  |
|--------------------|-----------|
| user1@test.com     | Pass123   |
| user2@test.com     | Pass456   |
| invalid@test.com   | wrongPass |

Row 0 = Header (will be skipped by ExcelReader)
Row 1+ = Test data (each row = one test execution)

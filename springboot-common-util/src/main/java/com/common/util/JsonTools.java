package com.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonTools {
    public static void main(String[] args) {
        String str = "{  \"total\": 48,  \"rows\": [    {      \"Base_EmployeeId\": \"3bdaec15-1314-4acd-8d4e-808df7bb4949\",      \"PunchTime\": new Date(        1415147833473      ),      \"WorkPunchTypeDb\": 0,      \"WorkPunchTypeName\": \"上班\",      \"MachineNo\": \"\",      \"PunchIp\": \"10.1.101.31\",      \"PunchMac\": \"C8:1F:66:F9:08:73\",      \"Base_Employee\": {        \"Code\": \"1000800\",        \"Name\": \"罗会军\",        \"EName\": \"Huijun.Luo\",        \"Base_DepartmentId\": \"27e2495a-5db6-4b2b-8613-23baf7b6b41e\",        \"Base_PositionId\": \"ec29b8a7-58f6-4c7c-9f3e-53720a2df934\",        \"GenderDb\": 1,        \"GenderName\": \"男\",        \"Birthday\": new Date(          612979200000        ),        \"IsMarriage\": true,        \"Mobile\": \"18665947828\",        \"OfficePhone\": null,        \"Email\": null,        \"Fax\": null,        \"EmployeeTypeDb\": 0,        \"EmployeeTypeName\": \"正式\",        \"EmpStatusDb\": 0,        \"EmpStatusName\": \"在职\",        \"Memo\": \"\",        \"Base_PositionName\": \"\",        \"Base_DepartmentName\": \"\",        \"Base_Position\": null,        \"Base_Department\": null,        \"Id\": \"3bdaec15-1314-4acd-8d4e-808df7bb4949\",        \"CreateUserId\": null,        \"ModifyUserId\": null,        \"CreateDate\": new Date(          1402616602000        ),        \"ModifyDate\": new Date(          1402616602000        ),        \"CreateUserName\": \"\",        \"ModifyUserName\": \"\",        \"IsDeleted\": false,        \"IsDraft\": false,        \"ApprovalStatusDb\": 0,        \"ApprovalStatusName\": \"审核通过\"      },      \"Base_EmployeeName\": \"罗会军\",      \"CustomField1\": null,      \"CustomField2\": null,      \"Id\": \"09d82b0f-dcc0-4d64-b230-a3da008e0f76\",      \"CreateUserId\": \"3bdaec15-1314-4acd-8d4e-808df7bb4949\",      \"ModifyUserId\": \"3bdaec15-1314-4acd-8d4e-808df7bb4949\",      \"CreateDate\": new Date(        1415147833563      ),      \"ModifyDate\": new Date(        1415147833563      ),      \"CreateUserName\": \"罗会军\",      \"ModifyUserName\": \"罗会军\",      \"IsDeleted\": false,      \"IsDraft\": false,      \"ApprovalStatusDb\": 1,      \"ApprovalStatusName\": \"待提交\"    }  ],  \"metaData\": {    \"root\": \"rows\",    \"totalProperty\": \"total\"  }}";
        str = str.replaceAll("new Date", "");
        str = str.replaceAll("\\(", "");
        str = str.replaceAll("\\)", "");
        System.out.println(str);
        JSONObject obj = JSONObject.fromObject(str);

        JSONArray jsonArray = JSONArray.fromObject(obj.get("rows"));
        JSONObject object = jsonArray.getJSONObject(0);
        System.out.println(object.get("Base_EmployeeId"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1415148775747l);
        System.out.println(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(calendar.getTime()));

    }
}

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Email</title>
    <style>
        table {
            width:800px;
            margin:0 auto;
        }
        .emailName {
            font-size:28px;
        }
        .user td{
            font-size:20px;
        }
        .user td .layout  {
            display:inline-block;
            text-align:right;
            margin-right:25px;
            width:80px;
            text-align: right;
        }
        .userName {
            font-size:24px;
        }

    </style>
</head>
<body style="padding: 0;margin: 0;">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
    <tr>
        <td>
            <table width="100%"  border="0" align="center" style="border-collapse: collapse" cellpadding="10">
                <thead>
                <tr>
                    <td class="emailName" bgcolor="#FF8523"><span style="color:#fff;padding-left: 20px">魔电致供应商</span></td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="userName">
                        ${enterpriseName}，您的帐户密码成功生成，拿上它寻找合适您的项目吧！
                    </td>
                </tr>
                <tr class="user" bgcolor="#FFFCCD">
                    <td ><div class="layout">用户名:</div>${username}</td>
                </tr>
                <tr class="user" bgcolor="#FFFCCD">
                    <td ><div class="layout">密码:</div>${password}</td>
                </tr>
                <tr bgcolor="#FFFCCD">
                    <td>别忘了访问：<a href="https://supplier.modianli.com/login">https://supplier.modianli.com/login</a></td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
</table>

</body>
</html>
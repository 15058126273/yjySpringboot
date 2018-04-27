<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>登录列表</title>
    <link rel="stylesheet" type="text/css" href="${base}/common/back/Style/skin.css"/>
    <script src="${base}/common/js/jquery-1.7.2.min.js" type="text/javascript"></script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <!-- 头部开始 -->
    <tr>
        <td width="17" valign="top" background="${base}/common/back/Images/mail_left_bg.gif">
            <img src="${base}/common/back/Images/left_top_right.gif" width="17" height="29"/>
        </td>
        <td valign="top" background="${base}/common/back/Images/content_bg.gif">
            <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0"
                   background="${base}/common/back/Images/content_bg.gif">
                <tr>
                    <td height="31">
                        <div class="title">管理员登录列表</div>
                    </td>
                </tr>
            </table>
        </td>
        <td width="16" valign="top" background="${base}/common/back/Images/mail_right_bg.gif"><img
                src="${base}/common/back/Images/nav_right_bg.gif" width="16" height="29"/></td>
    </tr>
    <!-- 中间部分开始 -->
    <tr>
        <!--第一行左边框-->
        <td valign="middle" background="${base}/common/back/Images/mail_left_bg.gif">&nbsp;</td>
        <!--第一行中间内容-->
        <td valign="top" bgcolor="#F7F8F9">
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <!-- 空白行-->
                <tr>
                    <td colspan="2" valign="top">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td valign="top">&nbsp;</td>
                </tr>
                <!-- 一条线 -->
                <tr>
                    <td height="40" colspan="4">
                        <table width="100%" height="1" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
                            <tr>
                                <td></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <!-- 产品列表开始 -->
                <tr>
                    <td width="2%">&nbsp;</td>
                    <td width="96%">
                        <table width="100%">
                            <tr>
                                <td colspan="2">
                                    <table width="100%" class="cont tr_color">
                                        <tr>
                                            <th>用户名</th>
                                            <th>登录时间</th>
                                            <th>IP</th>
                                            <th>登录状态</th>
                                            <th>用户类型</th>
                                        </tr>
                                        <#if pagination??>
                                            <#list pagination.list as user>
                                        <tr align="center" class="d">
                                            <td>${user.name!}</td>
                                            <td>${(user.loginDate?datetime)!}</td>
                                            <td>${user.loginIp!}</td>
                                            <td><#if user?? && user.category?? && user.category==1>成功<#else>
                                                失败</#if></td>
                                            <td><#if user?? && user.userType?? && user.userType==0>超级管理员<#else>
                                                普通管理员</#if></td>
                                        </tr>
                                            </#list>
                                        <#else>
                            <tr>
                                <td colspan="5" style="text-align: center;">
                                    <div class="">暂无数据</div>
                                </td>
                            </tr>
                                        </#if>
                                        <form action="${backBase}/admin/loginList.do" id="paginationForm" method="post">
                                            <input type="hidden" value="${pageNo!}" id="paginationNo" name="pageNo"/>
                                            <input type="hidden" value="${keyWords!}" name="keyWords"/>
                                        </form>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td width="2%">&nbsp;</td>
                </tr>
                <!-- 产品列表结束 -->
                <tr>
                    <td height="40" colspan="4">
                        <table width="100%" height="1" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
                            <tr>
                                <td></td>
                            </tr>
                        </table>
                    </td>
                </tr>


            </table>
        </td>
        <td background="${base}/common/back/Images/mail_right_bg.gif">&nbsp;</td>
    </tr>
    <!-- 底部部分 -->
    <tr>
        <td valign="bottom" background="${base}/common/back/Images/mail_left_bg.gif">
            <img src="${base}/common/back/Images/buttom_left.gif" width="17" height="17"/>
        </td>
        <td background="${base}/common/back/Images/buttom_bgs.gif">
            <img src="${base}/common/back/Images/buttom_bgs.gif" width="17" height="17">
        </td>
        <td valign="bottom" background="${base}/common/back/Images/mail_right_bg.gif">
            <img src="${base}/common/back/Images/buttom_right.gif" width="16" height="17"/>
        </td>
    </tr>
</table>
<#include "../include/pagination.ftl"/>
</body>
<script type="text/javascript">
    var message = "${message!}";
    $(function () {
        if ("" != message) {
            alert(message);
        }
    });
</script>
</html>
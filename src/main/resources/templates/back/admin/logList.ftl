<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="${base}/common/back/Style/skin.css"/>
    <link rel="stylesheet" href="${base}/common/date/datepicker.min.css">
    <script src="${base}/common/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${base}/common/date/back/datepicker.js"></script>
    <script type="text/javascript" src="${base}/common/date/back/i18n/datepicker.zh.js"></script>
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
                        <div class="title">操作列表</div>
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
                <tr>
                    <td colspan="4">
                        <table>
                            <form action="${backBase}/admin/logList.do" method="post">
                                <input type="text" name="itemName" placeholder="请输入标题" value="${itemName!}"/>&nbsp;
                                &nbsp;
                                <input type="text" name="operator" placeholder="请输入操作员" value="${operator!}"/>&nbsp;
                                &nbsp;
                                <select name="otype" id="otype" class="required">
                                    <option value="">操作类型</option>
                                    <option value="1" <#if otype?? && otype==1>selected</#if>>添加</option>
                                    <option value="2"  <#if otype?? && otype==2>selected </#if>>更新</option>
                                    <option value="3" <#if otype?? && otype==3>selected </#if>>启用</option>
                                    <option value="4" <#if otype?? && otype==4>selected </#if>>停用</option>
                                    <option value="5" <#if otype?? && otype==5>selected </#if>>删除</option>
                                    <option value="6" <#if otype?? && otype==6>selected  </#if>>发货</option>
                                </select>
                                &nbsp;&nbsp;
                                <select name="itemType" id="itemType" class="required">
                                    <option value="">操作项类型</option>
                                    <option value="1" <#if itemType?? && itemType==1>selected</#if>>用户</option>
                                    <option value="2" <#if itemType?? && itemType==2>selected</#if>>任务</option>
                                    <option value="3" <#if itemType?? && itemType==3>selectet</#if>>签到任务</option>
                                    <option value="41" <#if itemType?? && itemType==41>selected</#if>>问卷</option>
                                    <option value="42" <#if itemType?? && itemType==42>selected </#if>>问卷题目</option>
                                    <option value="43" <#if itemType?? && itemType==43>selected </#if>>问卷题目选项</option>
                                    <option value="44" <#if itemType?? && itemType==44>selected</#if>>问卷详情</option>
                                    <option value="45" <#if itemType?? && itemType==45>selected</#if>>生成问卷</option>
                                    <option value="5" <#if itemType?? && itemType==5>selected</#if>>游戏</option>
                                    <option value="6" <#if itemType?? && itemType==6>selected</#if>>活动</option>
                                    <option value="7" <#if itemType?? && itemType==7>selected</#if>>抽奖管理</option>
                                    <option value="8" <#if itemType?? && itemType==8>selected</#if>>用户奖品</option>
                                    <option value="9" <#if itemType?? && itemType==9>selected</#if>>奖品</option>
                                    <option value="10" <#if itemType?? && itemType==10>selected</#if>>首页banner图</option>
                                </select>
                                &nbsp;&nbsp;
                                开始时间：<input class="datepicker-here" name="startDate" type="text"
                                            value="${(startDate)!}">
                                &nbsp;&nbsp;
                                结束时间：<input class="datepicker-here" type="text" name="endDate" value="${(endDate)!}">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="submit" value="确定"/>
                            </form>
                        </table>
                    </td>
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
                                            <th>操作类型</th>
                                            <th>操作项类型</th>
                                            <th>操作员</th>
                                            <th>操作时间</th>
                                            <th>操作项标题</th>
                                            <th>ip</th>
                                        </tr>
                        <#if pagination??>
                            <#list pagination.list as bean>
                        <tr align="center" class="d">
                            <td>
                                <#if bean?? && bean.operateType??>
                                <#if bean.operateType==1>新增</#if>
                            <#if bean.operateType==2>修改</#if>
                        <#if bean.operateType==3>启用</#if>
                    <#if bean.operateType==4>停用</#if>
                <#if bean.operateType==5>删除</#if>
            <#if bean.operateType==6>发货</#if>
                                <#else> 无类型</#if>
                            </td>
                            <td>
        <#if bean?? && bean.itemType??>
        <#if bean.itemType==1>用户</#if>
    <#if bean.itemType==2>任务</#if>
<#if bean.itemType==3>签到任务</#if>
<#if bean.itemType==41>问卷</#if>
<#if bean.itemType==42>问卷题目</#if>
<#if bean.itemType==43>问卷题目选项</#if>
<#if bean.itemType==44>问卷详情</#if>
<#if bean.itemType==45>生成问卷</#if>
<#if bean.itemType==5>游戏</#if>
<#if bean.itemType==6>活动</#if>
<#if bean.itemType==7>抽奖管理</#if>
<#if bean.itemType==8>用户奖品</#if>
<#if bean.itemType==9>奖品</#if>
<#if bean.itemType==10>首页banner图</#if>
        <#else>无类型</#if>
                            </td>
                            <td>${bean.operator!}</td>
                            <td>${bean.operateDate!}</td>
                            <td>${bean.itemName!}</td>
                            <td>${bean.ip!}</td>
                        </tr>

                            </#list>
                        <#else>
<tr align="center" class="d" col>
    <td colspan="6">
        <div>
            <div style="margin:20px;">暂无数据</div>
        </div>
    </td>
</tr>
                        </#if>
                                        <form action="${backBase }/admin/logList.do" id="paginationForm" method="post">
                                            <input type="hidden" value="" id="paginationNo" name="pageNo"/>
                                            <input type="hidden" value="${itemName!}" name="itemName"/>
                                            <input type="hidden" value="${operator!}" name="operator"/>
                                            <input type="hidden" value="${otype!}" name="otype"/>
                                            <input type="hidden" value="${itemType!}" name="itemType"/>
                                            <input type="hidden" value="${startDate!}" name="startDate"/>
                                            <input type="hidden" value="${endDate!}" name="endDate"/>
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
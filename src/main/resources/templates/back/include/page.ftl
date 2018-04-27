<!-- 将分页的参数全部加到页面的form表单中 -->

<input type="hidden" id="alertMsg" value="${msg!}"/>
<#if pagination?? && pagination.list?? >
    <#assign p=pagination />
<div class="changeBox">
    <input type="hidden" value="" id="paginationNo" name="pageNo"/>
    <div>
        当前${(p.pageNo)!}/${(p.totalPage)!}页，共 ${p.totalCount } 条记录
    </div>
    <ul>
        <li><a class="pageBtn" data-info="1">首页</a></li>
        <#if p.firstPage>
        <li class="noSelected">上一页</li>
        <#else>
        <li><a class="pageBtn" data-info="${(p.prePage)!}">上一页</a></li>
        </#if>
    <#if p.lastPage>
    <li class="noSelected">下一页</li>
    <#else>
    <li><a class="pageBtn" data-info="${(p.nextPage)!}">下一页</a></li>
    </#if>
        <li>
            <a class="pageBtn" data-info="${(p.totalPage)!}">尾页</a>
        </li>
        <li class="userPage">
            <input id="myCurrentPage" type="text" value="${p.pageNo }" onkeypress="return IsNum(event)"/>
        </li>
        <li class="btnPage">
            跳转
        </li>
    </ul>
</div>
<div class="clearfix"></div>
<script type="text/javascript">
    $(function () {
        $(".pageBtn").click(function () {
            var pageNo = $(this).attr("data-info");
            console.log(pageNo)
            if (pageNo === undefined) {
                pageNo = $("#myCurrentPage").val();
                if (!/^\d+$/.test(pageNo)) {
                    alert("请输入正确的页数");
                    return;
                }
            }

            $("#paginationNo").val(pageNo);
            $("#paginationForm").submit();
        });

        $(".btnPage").click(function () {
            var pageNo = $("#pageNum").val();
            if (pageNo === undefined) {
                pageNo = $("#myCurrentPage").val();
                if (!/^\d+$/.test(pageNo)) {
                    alert("请输入正确的页数");
                    return;
                }
            }

            $("#paginationNo").val(pageNo);
            $("#paginationForm").submit();
        });

        function IsNum(e) {
            var k = window.event ? e.keyCode : e.which;
            if (((k >= 48) && (k <= 57)) || k == 8 || k == 0) {
            } else {
                if (window.event) {
                    window.event.returnValue = false;
                }
                else {
                    e.preventDefault(); //for firefox
                }
            }
        }

    });
</script>
</#if>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){
		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});
		
		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});
		
		$(".remarkDiv").mouseover(function(){
			$(this).children("div").children("div").show();
		});
		
		$(".remarkDiv").mouseout(function(){
			$(this).children("div").children("div").hide();
		});
		
		$(".myHref").mouseover(function(){
			$(this).children("span").css("color","red");
		});
		
		$(".myHref").mouseout(function(){
			$(this).children("span").css("color","#E6E6E6");
		});

        $("#remarkBody").on("mouseover",".remarkDiv",function(){
            $(this).children("div").children("div").show();
        })
        $("#remarkBody").on("mouseout",".remarkDiv",function(){
            $(this).children("div").children("div").hide();
        })
        showRemarkList();
        //为保存按钮绑定事件，执行备注的添加操作
        $("#saveRemarkBtn").click(function () {
            $.ajax({
                url:"workbench/activity/saveRemark.do",
                data:{
                    "noteContent":$.trim($("#remark").val()),
                    "activityId":"${a.id}"
                },
                type:"post",
                dataType:"json",
                success:function (data) {
                    /*
                    data:
                       {success:true/false}
                     */
                    if (data.success){
                        //清空之前操作的内容
                        $("#remark").val("");

                        var html="";
                        //添加成功，在textarea文本域上方新增加一个div
                        html+='<div id="'+data.ar.id+'" class="remarkDiv" style="height: 60px;">';
                        html+='<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">';
                        html+='<div style="position: relative; top: -40px; left: 40px;" >';
                        html+='<h5 id="e'+data.ar.id+'">'+data.ar.noteContent+'</h5>';
                        html+='<font color="gray">市场活动</font> <font color="gray">-</font> <b>${a.name}</b> <small style="color: gray;"> '+(data.ar.createTime)+' 由'+(data.ar.createBy)+'</small>';
                        html+='<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">';
                        html+='<a class="myHref" href="javascript:void(0);" onclick="editRemark(\''+data.ar.id+'\')"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #FF0000;"></span></a>';
                        html+='&nbsp;&nbsp;&nbsp;&nbsp;';
                        html+='<a class="myHref" href="javascript:void(0);" onclick="deleteRemark(\''+data.ar.id+'\')"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #FF0000;"></span></a>';
                        html+='</div>';
                        html+='</div>';
                        html+='</div>';
                        $("#remarkDiv").before(html);

                    } else {
                        alert("添加失败")
                    }
                }
            })
        })

        //为更新绑定事件
        $("#updateRemarkBtn").click(function () {
            var id=$("#remarkId").val();
            var noteContent=$.trim($("#noteContent").val());
            $.ajax({
                url:"workbench/activity/updateRemark.do",
                data:{
                    "id":id,
                    "noteContent":noteContent
                },
                type:"post",
                dataType:"json",
                success:function (data) {
                    /*
                    data
                       {"success":true/false,"ar":{备注}}
                     */
                    if (data){
                        //修改备注成功
                        //更新div中相应的信息，需要更新的内容有 noteContent，editTime,editBy
                        //这里还是用一个ar表示，更上一个ar不是一个
                        $("#e"+id).html(data.ar.noteContent);
                        $("#s"+id).html((data.ar.editTime)+"由"+(data.ar.editBy));
                        //关闭模态窗口
                        $("#editRemarkModal").modal("hide");
                    } else {
                        alert("修改失败失败")
                    }
                }
            })
        })

        //编辑市场活动模态窗口
        $("#edit-Btn").click(function () {

            $("#edit-marketActivityOwner").html("<option>${a.owner}</option>")
            $("#edit-marketActivityName").val("${a.name}")
            $("#edit-startTime").val("${a.startDate}")
            $("#edit-endTime").val("${a.endDate}")
            $("#edit-cost").val("${a.cost}")
            $("#edit-describe").html("${a.description}")
            $("#editActivityModal").modal("show");
        })
        //更新市场活动
        $("#updateActivity").click(function () {

            var id="${a.id}";
            var owner="${user.id}";
            var name=$("#edit-marketActivityName").val();
            var startDate=$("#edit-startTime").val();
            var endDate=$("#edit-endTime").val();
            var cost=$("#edit-cost").val();
            var description=$("#edit-describe").val();
            $.ajax({
                url:"workbench/activity/update.do",
                data:{
                    "id":id,
                    "owner":owner,
                    "name":name,
                    "startDate":startDate,
                    "endDate":endDate,
                    "cost":cost,
                    "description":description
                },
                type:"get",
                dataType:"json",
                success:function (data) {
                    if (data){
                        $("#editActivityModal").modal("hide")
                    } else {
                        alert("修改失败")
                    }
                }
            })
        })




	});
    <%--//活动具体信息--%>
    // <!--
	 //     这个地方本来向玩一下，动态的加载活动的信息。
    //      但是我用的是el表达式从request域中取值。只有当点击进入活动页面时，才往request域中放入相应的对象，
    //      我修改之后，没办法再前端更改request域中的内容，所以没办法让修改后的内容直接刷新出来
    //  想要刷新必须再重新进来一次。更新request作用域中的内容
    //  想要直接刷新这个页面的话，从index.jsp 到detail.jsp 不能用转发的形式。再这个页面也就不能用el表达式的形式取值
    //  就算了，就没写了
    // -->
    <%--function showActivity() {--%>
    <%--$("#load-owner").html("${a.owner}");--%>
    <%--$("#load-name").html("${a.name}");--%>
    <%--$("#load-startDate").html("${a.startDate}");--%>
    <%--$("#load-endDate").html("${a.endDate}");--%>
    <%--$("#load-cost").html("${a.cost}");--%>
    <%--$("#load-createBy").html("${a.createBy}");--%>
    <%--$("#load-createTime").html("${a.createTime}");--%>
    <%--$("#load-editBy").html("${a.editBy}");--%>
    <%--$("#load-editTime").html("${a.editTime}");--%>
    <%--$("#load-description").html("${a.description}");--%>
    <%--}--%>
     function showRemarkList() {
		 $.ajax({
			 url:"workbench/activity/getRemarkListByAid.do",
			 data:{
			     "activityId":"${a.id}"
			 },
			 type:"get",
			 dataType:"json",
			 success:function (data) {
				 var html="";
				 $.each(data,function (i,n) {
                     /**
					  * href="javascript:void(0)
					  *    表示禁用超链接，只能以触发事件的方式操作
					  *    在遍历中动态生成的元素没办法根据id绑定事件触发，永远都是直接触发的操作
                      */
                     html+='<div id="'+n.id+'" class="remarkDiv" style="height: 60px;">';
                     html+='<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">';
                     html+='<div style="position: relative; top: -40px; left: 40px;" >';
                     html+='<h5 id="e'+n.id+'">'+n.noteContent+'</h5>';
                     html+='<font color="gray">市场活动</font> <font color="gray">-</font> <b>${a.name}</b> <small style="color: gray;" id="s'+n.id+'"> '+(n.editFlag==0?n.createTime:n.editTime)+' 由'+(n.editFlag==0?n.createBy:n.editBy)+'</small>';
                     html+='<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">';
                     html+='<a class="myHref" href="javascript:void(0);" onclick="editRemark(\''+n.id+'\')"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #FF0000;"></span></a>';
                     html+='&nbsp;&nbsp;&nbsp;&nbsp;';
				     html+='<a class="myHref" href="javascript:void(0);" onclick="deleteRemark(\''+n.id+'\')"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #FF0000;"></span></a>';
                     html+='</div>';
                     html+='</div>';
                     html+='</div>';
                 })
				 $("#remarkDiv").before(html);
             }
		 })
         }
         //删除备注操作
    function deleteRemark(id) {
         //动态生成的元素，id必须放在字符串中。而单引号又和最前面的单引号有歧义，加\转义。再加''
		$.ajax({
            url:"workbench/activity/deleteActivityRemarkById.do",
            data:{
                "id":id
            },
            type:"post",
            dataType:"json",
            success:function (data) {
                if (data	){
                    $("#"+id).remove();
                    //showRemarkList();
                }else {
                    alert("删除失败")
                }
            }
        })
     }
     //修改备注操作
    function editRemark(id) {
         //将模态窗口隐藏域中的id附上值,为后面做更新操作时，知道是要更新的是那一条信息
        $("#remarkId").val(id)
         //找到指定存放备注信息的(h5)标签，拿到标签对中的内容。放在文本域中。
         $("#noteContent").val($("#e"+id).html());
         $("#editRemarkModal").modal("show")

    }
</script>

</head>
<body>
	
	<!-- 修改市场活动备注的模态窗口 -->
	<div class="modal fade" id="editRemarkModal" role="dialog">
		<%-- 备注的id --%>
		<input type="hidden" id="remarkId">
        <div class="modal-dialog" role="document" style="width: 40%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">修改备注</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">内容</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="noteContent"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="updateRemarkBtn">更新</button>
                </div>
            </div>
        </div>
    </div>

    <%--<!-- 修改市场活动的模态窗口 -->--%>
    <div class="modal fade" id="editActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">修改市场活动</h4>
                </div>
                <div class="modal-body">

                    <form class="form-horizontal" role="form">

                        <div class="form-group">
                            <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-marketActivityOwner">
                                    <option>zhangsan</option>
                                    <option>lisi</option>
                                    <option>wangwu</option>
                                </select>
                            </div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-startTime" value="2020-10-10">
                            </div>
                            <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-endTime" value="2020-10-20">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-cost" value="5,000">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
                            </div>
                        </div>

                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary"  id="updateActivity">更新</button>
                </div>
            </div>
        </div>
    </div>

	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>市场活动-${a.name} <small>${a.startDate} ~ ${a.endDate}</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 250px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" data-toggle="modal" id="edit-Btn"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${a.owner}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${a.name}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>

		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">开始日期</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${a.startDate}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">结束日期</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${a.endDate}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">成本</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${a.cost}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${a.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${a.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${a.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${a.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${a.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
	</div>
	
	<!-- 备注 -->
	<div id="remarkBody" style="position: relative; top: 30px; left: 40px;">
		<div class="page-header">
			<h4>备注</h4>
		</div>
		
		<%--<!-- 备注1 -->--%>
		<%--<div class="remarkDiv" style="height: 60px;">--%>
			<%--<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">--%>
			<%--<div style="position: relative; top: -40px; left: 40px;" >--%>
				<%--<h5>哎呦！</h5>--%>
				<%--<font color="gray">市场活动</font> <font color="gray">-</font> <b>发传单</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>--%>
				<%--<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">--%>
					<%--<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>--%>
					<%--&nbsp;&nbsp;&nbsp;&nbsp;--%>
					<%--<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%----%>
		<%--<!-- 备注2 -->--%>
		<%--<div class="remarkDiv" style="height: 60px;">--%>
			<%--<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">--%>
			<%--<div style="position: relative; top: -40px; left: 40px;" >--%>
				<%--<h5>呵呵！</h5>--%>
				<%--<font color="gray">市场活动</font> <font color="gray">-</font> <b>发传单</b> <small style="color: gray;"> 2017-01-22 10:20:10 由zhangsan</small>--%>
				<%--<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">--%>
					<%--<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>--%>
					<%--&nbsp;&nbsp;&nbsp;&nbsp;--%>
					<%--<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
		
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary" id="saveRemarkBtn">保存</button>
				</p>
			</form>
		</div>
	</div>
	<div style="height: 200px;"></div>
</body>
</html>
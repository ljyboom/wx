<#assign ctx=request.contextPath />
<!DOCTYPE html>
<html lang="en">
<head>
    <title>搞笑视频</title>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1, user-scalable=no,width=device-width,height=device-height">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <link href="${ctx}/css/video-js.min.css" rel="stylesheet" type="text/css">
    <style type="text/css">
    .in{

    }
    </style>
</head>
<body style="background-color: #ffffff">
<div id="videoId"></div>
<div  id="loading" class="in">
    <img src="${ctx}/images/loading.gif" width="100%">
</div>
<div style="width: 100%;display: none;" id="more"><p style="text-align: center;">更多>></p></div>
</body>
<script type="text/javascript" src="${ctx}/js/video.min.js"></script>
<script type="text/javascript">
    var width=document.documentElement.clientWidth;
    var height=document.documentElement.clientHeight;
    var page=0;//页码
    var getInfo=function(page,rows) {
        $.ajax({
            url: "${ctx}/getVideos",
            type: "get",
            dataType: "json",
            data: {page: page, rows: rows,type:1},
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    var uid = data[i].uid.toString();
                    var title = data[i].title;//视频标题
                    var img = "'" + data[i].img + "'";//图片
                    var url = data[i].url;//图片地址
                    var t_url="${ctx}/images/t.png";
                    $("#videoId").append("<div" +
                            "<p style='color:#2a2a2a;font-size:15px'><img width='15'height='15' src="+t_url+">" + title + "</p>" +
                            " <video id=" + uid + " class='video-js vjs-default-skin vjs-big-play-centered' controls preload='auto'" +
                            " webkit-playsinline='true' playsinline='true' x-webkit-airplay='true' width=" + width + " height=" + height / 3 + "" +
                            " style=background:url(" + img + ");background-size:100%100%;object-fit:cover;>" +
                            "<source src=" + url + " type='video/mp4' />" +
                            "</video></div><br><hr>");
                    videojs(uid, {}, function () {});

                }
                $("#loading").css("display","none");
                $("#more").css("display","block");
            }
        });
    };
    getInfo(page,10);
    $("#more").click(function () {
        page++;
        getInfo(page,10);
    });

</script>
</html>
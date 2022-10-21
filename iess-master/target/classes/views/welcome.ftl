<!DOCTYPE html>
<html>
<head>
  <#include "common.ftl">
</head>
<style>
    @font-face {
        font-family: "iconfont"; /* Project id 2516453 */
        src: url("//at.alicdn.com/t/font_2516453_g6qjhhqblt9.woff2?t=1620545333370")
        format("woff2"),
        url("//at.alicdn.com/t/font_2516453_g6qjhhqblt9.woff?t=1620545333370")
        format("woff"),
        url("//at.alicdn.com/t/font_2516453_g6qjhhqblt9.ttf?t=1620545333370")
        format("truetype");
    }
    .iconfont {
        font-family: "iconfont" !important;
        font-size: 16px;
        font-style: normal;
        -webkit-font-smoothing: antialiased;
        -webkit-text-stroke-width: 0.2px;
        -moz-osx-font-smoothing: grayscale;
        margin: 0;
    }
    i {
        color: #ff6146;
        font-size: 20px;
        margin-right: 10px;
    }
    #barck{
        background-image: url("https://pic.616pic.com/bg_w1180/00/00/75/tVWcO1MZq7.jpg");
        background-size: cover;
    }
    div img{
        width: 100%;
        height: 100%;
        object-fit:cover;
    }
</style>

<body class="childrenBody">

<div class="layui-tab-item layui-show">
<#--    <div class="layui-carousel" id="test10" align="center">-->
<#--        <div carousel-item="">-->
<#--            <div><img src="${ctx}/images/timg.jpeg" style="width:100%"></div>-->
<#--        </div>-->
<#--    </div>-->
    <i class="iconfont" style="padding-bottom:2px ">&#xe633;</i>
    <marquee  direction="left" behavior="scroll"  scrollamount="10" onmouseover="this.stop();" onmouseout="this.start();" scrolldelay="0" loop="-1" width="1300" height="20px" style="padding-top: 4px;">
<#--        欢迎使用面经管理系统，欢迎大家踊跃发言，如有疑问请联系管理，QQ：123456789，谢谢 &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp 请多多关注通知，谢谢-->
<#--        <input type="text" class="layui-textarea" lay-verify="required" value="${(announceInfo.announce)!}"/>-->
        <p>${(announceInfo.announce)!}</p>
    </marquee>

    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend style="font-size: 30px">公告</legend>
    </fieldset>
    <div>


        <div class="layui-carousel" id="test10" style="margin: 0 auto">
            <div carousel-item="">
                <div>
                    <img src="https://ts1.cn.mm.bing.net/th/id/R-C.a05d0f489e91e00439bf1abb89da7928?rik=QRcrHcqTH%2fn3GA&riu=http%3a%2f%2fimg01.51jobcdn.com%2fim%2fmk%2fartimages%2f154512.jpg&ehk=Lv1wDsIM6YkOxqZ3icSU36fyXWr2pvI6zD0DkDeoD%2f0%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1">
                </div>
                <div id="barck">
                    <p style="text-align: center;font-size: 25px">公告1</p>
                    <p style="font-size: 20px;padding: 20px;line-height: 25px;text-indent: 2em">
                        本系统将在10月10日正式上线，欢迎各位打工仔们来使用本系统！
                    </p>
                </div>
                <div id="barck">
                    <p style="text-align: center;font-size: 25px">公告2</p>
                    <p style="font-size: 20px;padding: 20px;line-height: 25px;text-indent: 2em">
                        本系统旨在交流分享经验，切勿传播色情、淫秽、侮辱、反华等内容！违者必究！！！
                    </p>
                </div>
                <div>
                    <img src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fww2.sinaimg.cn%2Fmw690%2F006jYLmbly1gjpqz12u0vj30b404x3yr.jpg&refer=http%3A%2F%2Fwww.sina.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614327495&t=8a62df437f60c89dc20ef4d511c685d8">
                </div>
            </div>
        </div>
    </div>
    <br>
    <br>


</div>

<script>
    layui.use(['carousel', 'form'], function(){
        var carousel = layui.carousel
            ,form = layui.form;

        //常规轮播
        carousel.render({
            elem: '#test1'
        });

        //改变下时间间隔、动画类型、高度
        carousel.render({
            elem: '#test2'
            ,interval: 1800
            ,anim: 'fade'
            ,height: '120px'
        });

        //设定各种参数
        var ins3 = carousel.render({
            elem: '#test3'
        });
        //图片轮播
        carousel.render({
            elem: '#test10'
            ,width: '720px'
            ,height: '360px'
            ,interval: 3000
        });

        //事件
        carousel.on('change(test4)', function(res){
            console.log(res)
        });

        var $ = layui.$, active = {
            set: function(othis){
                var THIS = 'layui-bg-normal'
                    ,key = othis.data('key')
                    ,options = {};

                othis.css('background-color', '#5FB878').siblings().removeAttr('style');
                options[key] = othis.data('value');
                ins3.reload(options);
            }
        };

        //开关事件
        form.on('switch(autoplay)', function(){
            ins3.reload({
                autoplay: this.checked
            });
        });
        // 自动播放控制
        form.on('select(autoplay)', function (obj) {
            // debugger;
            var autoplayValue = parseInt(obj.value);
            ins3.reload({
                autoplay: isNaN(autoplayValue) ? obj.value : autoplayValue
            });
        })

        $('.demoSet').on('keyup', function(){
            var value = this.value
                ,options = {};
            if(!/^\d+$/.test(value)) return;

            options[this.name] = value;
            ins3.reload(options);
        });

        //其它示例
        $('.demoTest .layui-btn').on('click', function(){
            var othis = $(this), type = othis.data('type');
            active[type] ? active[type].call(this, othis) : '';
        });
    });
</script>
</body>
</html>

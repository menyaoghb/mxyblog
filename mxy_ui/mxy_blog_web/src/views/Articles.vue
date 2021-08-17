<template>
    <div class="articles">
        <!--<banner></banner>-->
        <div class="site-content animate" style="display: flex;justify-content: center;align-items: center;">
            <!-- 文章目录 -->
            <!--            <div id="article-menus">
                            <menu-tree :menus="menus" child-label="child"></menu-tree>
                        </div>-->
            <main class="site-main" style="width: 60%">
                <article class="hentry">
                    <!-- 文章头部 -->
                    <header class="entry-header">
                        <!-- 标题输出 -->
                        <h1 class="entry-title">看一遍闭着眼都会安装Lua了</h1>
                        <hr>
                        <div class="breadcrumbs">
                            <div id="crumbs">最后更新时间：2020年04月21日</div>
                        </div>
                    </header>
                    <!-- 正文输出 -->
                    <div class="entry-content">
                        <p><strong><u>1.</u></strong><strong><u>申诉全量数据推送：</u></strong></p>
                        <p>
                            （1）正常情况：在认定员处理前，先把全量数据（携带工单唯一标识符ID号码，22位、纯数字），下发给省分，仅支持省分录入，不支持省分回单、不支持省分申辩等，该环节工单无需回复集团，无需回复部中心，工单不发系统通知（）。</p>
                        <p>①针对没有分类码的申诉工单，，要保留可查询，可导出功能。</p>
                        <p>②该工单自动派单给省分，处理过程中，如果工单改为预处理类型工单，原。</p>
                        <p>③部中心将内部人员分单时间调长至30分钟，撤诉工单5分钟（如果单独给联通调带撤诉时间，不更改另外两家的撤诉时间，需单独给部中心提交需求，对第三方保密）。</p>
                        <p>（2）异常情况：</p>
                        <p>①无。</p>
                        <p>②如果遇到异常情况，无分类码的工单，没有正常下发，但是下一个阶段的工单无法在工单上进行新增内容。（提醒申云及软研院考虑此种情况发生，请双方补充该状态下细化的处理方式）</p>
                        <p><strong><u>2.</u></strong><strong><u>并单：</u></strong></p>
                        <p>（1）并案的子单A2根据部系统工单ID号码识别，直接在并案的主单（A1）上进行增量内容的同步，并通知省分主A1工单信息变更。</p>
                        <p>①在主单中增加操作日志。</p>
                        <p>②在主单申诉内容最下面，追加并案信息，原申诉内容不变，逐条新增&ldquo;并案人、并案内容、并案时间&rdquo;，并案几次增加几条信息，时间最近在最上面展示。</p>
                        <p>③在主单流水号前增加&ldquo;并案标识&rdquo;显示&ldquo;并&rdquo;，。或省分下发工单变更的业务通知，子单无需派省分。</p>
                        <p>（2）子单(A2)会生成全量工单(a2')，根据部中心传递的信息更新a2'，无需回部系统。</p>
                        <p>（3）业务通知下发内容，由业务侧提供，主题+内容。</p>
                        <p><strong><u>3.</u></strong><strong><u>预处理：</u></strong></p>
                        <p>
                            （1）31省预处理工单，根据部中心下发的工单类型、企业地区，在集团系统生成预处理类型工单，系统自动派单给省分；支持主办省协办给他省，协办省分只可退单给主办部门。，无需回复部中心，省分工单处理完成后，回单集团，集团人工办结；<strong>非属地省</strong>回单执行以下流程：
                        </p>
                        <p>（2）非属地省回单：</p>
                        <p><u>不</u><u>隐藏&ldquo;是否和解&rdquo;下拉菜单</u>：</p>
                        <p>①在现有的省分回单界面，如省分在&ldquo;是否和解&rdquo;处选择&ldquo;和解&rdquo;，该预处理环节工单，点击&ldquo;处理完成&rdquo;后直接回集团；如省分在&ldquo;是否和解&rdquo;处选择&ldquo;未和解&rdquo;，该预处理环节工单，点击&ldquo;处理完成&rdquo;后直接回部中心；</p>
                        <p>②选择&ldquo;是否申辩&rdquo;按钮工单：省分核实后，符合申辩场景，选择是否&ldquo;申辩&rdquo;下拉框，该预处理工单先判断是否有转办工单，如果无转办工单，点击&ldquo;处理完成&rdquo;按钮，该工单流转至集团处理人员；集团处理人员进行人工审核，审核通过，按钮直接回部系统，审核不通过，可点击&ldquo;集团退单&rdquo;按钮，工单退至省分，由省分继续二次处理，时限不变。如果有转办工单，省分不能选择&ldquo;申辩&rdquo;按钮，按未申辩流程处理。</p>
                        <p>③回复内容包含&ldquo;是否和解（未和解含未联系成功）、是否申辩及申辩场景&rdquo;和附件回复部中心。</p>
                        <p>
                            <img src="https://cdn.jsdelivr.net/npm/tinymce-all-in-one@4.9.3/plugins/emoticons/img/smiley-cool.gif"
                                 alt="cool"/></p>
                        <p>①选择&ldquo;申辩&rdquo;按钮工单：省分核实后，符合申辩场景，选择是否&ldquo;申辩&rdquo;下拉框，该预处理工单先判断是否有转办工单，如果无转办工单，点击&ldquo;处理完成&rdquo;按钮，该工单流转至集团处理人员；集团处理人员进行人工审核，审核通过，点击&ldquo;办结&rdquo;（新增按钮，名称再议）按钮直接回部系统，审核不通过，可点击&ldquo;集团退单&rdquo;按钮，工单退至省分，由省分继续二次处理，时限不变。如果有转办工单，省分不能选择&ldquo;申辩&rdquo;按钮，按未申辩流程处理。</p>
                        <p>②未选择&ldquo;申辩&rdquo;按钮工单：省分核实后，填写回复意见，点击&ldquo;处理完成&rdquo;按钮，该工单流转至集团处理人员，处理人员直接在集团系统办结（保持现有&ldquo;业务办结&rdquo;按钮，不回传部中心），无需回复部系统。</p>
                        <p>③回复部中心内容包含&ldquo;未和解、是否申辩及申辩场景&rdquo;和附件回复部中心。</p>
                        <p>（3）集团系统派单时限：工单类型，由系统自己配置完成。（王瀚配置）</p>
                        <p>计算派单时限：受理日期（取值申诉日期的值）+2.5个工作日。</p>
                        <p>
                            （4）预处理工单被转办后，部系统会下发一个结束指令，如果预处理工单在省分，等待省分处理完成后，工单直接办结，无需回复部系统；如果预处理工单在集团，无需回复部系统，集团系统直接工单办结，在集团系统中增加&ldquo;预处理超时办结&rdquo;字段，系统自动打标，可搜索、可提取明细。</p>
                        <p>（5）预处理工单：直接退至集团处理人员，由集团处理人员人工返单省分，省分处理完成后，再回到集团处理人员，集团审核后，回单部系统，针对返单工单，在集团系统中发送业务通知，在工单流水号前增加&ldquo;返&rdquo;标识，可通过搜索栏搜索&ldquo;返单&rdquo;提取相应工单流水号内容。</p>
                        <p>（6）即将超期提醒：部中心会调用接口，告知企业工单即将超时，集团系统进行工单超时提醒，发送业务通知至工单所在环节处理人、在工单流水号增加&ldquo;超&rdquo;标识，可通过搜索栏搜索&ldquo;超时&rdquo;提取相应工单流水号内容。</p>
                        <p>（7）预处理工单在部系统&ldquo;申辩&rdquo;回单后：（厂家通知企业，单独接口或一个接口均可）</p>
                        <p>①针对部中心认定员判定为&ldquo;无效&rdquo;状态工单：会将判定结果告知企业，企业接收到该类工单，增加的查询条件，包含&ldquo;预处理无效&rdquo;、&ldquo;预处理有效&rdquo;字段。</p>
                        <p>②针对部中心认定员：企业系统根据部系统推送的信息，更新（例如：部-1234***）工单状态（有效工单/无效工单、改属地）、、更新企业地区，企业系统工单办结。</p>
                        <p>
                            （8）&ldquo;部&rdquo;开头的申诉流水号的工单，除属地省外，非属地省申辩工单需要集团回复部中心；&ldquo;省分&rdquo;开头的申诉流水号工单，不需要回复部中心。</p>
                        <p>（9）异常状态：申诉日期超5个工作日工单未办结，集团可以提醒部中心（部中心、软研院技术人员补充相关内容）<strong>。</strong></p>


                    </div>
                    <!-- 文章底部 -->
                    <section-title>
                        <footer class="post-footer">
                            <!-- 阅读次数 -->
                            <div class="post-like">
                                <i class="iconfont iconeyes"></i>
                                <span class="count">685</span>
                            </div>
                            <!-- 分享按钮 -->
                            <!--                        <div class="post-share">-->
                            <!--                            <ul class="sharehidden">-->
                            <!--                                <li><a href="http://qr.liantu.com/api.php?text=https://zhebk.cn/Web/gongan.html"-->
                            <!--                                       onclick="window.open(this.href, 'renren-share', 'width=490,height=700');return false;"-->
                            <!--                                       class="s-weixin" target="_blank" rel="nofollow noopener noreferrer"><img src="https://cdn.zhebk.cn/usr/themes/Akina/images/wechat.png"></a></li>-->
                            <!--                                <li>-->
                            <!--                                    <a href="http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=https://zhebk.cn/Web/gongan.html/&amp;title=公安联网备案记录"-->
                            <!--                                       onclick="window.open(this.href, 'weibo-share', 'width=730,height=500');return false;"-->
                            <!--                                       class="s-qq" target="_blank" rel="nofollow noopener noreferrer"><img src="https://cdn.zhebk.cn/usr/themes/Akina/images/qzone.png"></a></li>-->
                            <!--                                <li>-->
                            <!--                                    <a href="http://service.weibo.com/share/share.php?url=https://zhebk.cn/Web/gongan.html/&amp;title=公安联网备案记录"-->
                            <!--                                       onclick="window.open(this.href, 'weibo-share', 'width=550,height=235');return false;"-->
                            <!--                                       class="s-sina" target="_blank" rel="nofollow noopener noreferrer"><img src="https://cdn.zhebk.cn/usr/themes/Akina/images/sina.png"></a></li>-->
                            <!--                                <li>-->
                            <!--                                    <a href="http://shuo.douban.com/!service/share?https://zhebk.cn/Web/gongan.html/&amp;title=公安联网备案记录"-->
                            <!--                                       onclick="window.open(this.href, 'renren-share', 'width=490,height=600');return false;"-->
                            <!--                                       class="s-douban" target="_blank" rel="nofollow noopener noreferrer"><img src="https://cdn.zhebk.cn/usr/themes/Akina/images/douban.png"></a></li>-->
                            <!--                            </ul>-->
                            <!--                            <i class="iconfont show-share"></i>-->
                            <!--                        </div>-->
                            <!-- 赞助按钮 -->
                            <!--<div class="donate" @click="showDonate=!showDonate">
                                <span>赏</span>
                                <ul class="donate_inner" :class="{'show':showDonate}">
                                    <li class="wedonate"><img src="http://cdn.fengziy.cn/gblog/wexin_pay.png">
                                        <p>微信</p></li>
                                    <li class="alidonate"><img src="http://cdn.fengziy.cn/gblog/ali_pay.jpg">
                                        <p>支付宝</p></li>
                                </ul>
                            </div>-->
                            <!-- 文章标签 -->
                            <!--<div class="post-tags">
                                <i class="iconfont iconcategory"></i>
                                <router-link to="/category/web">Web</router-link>
                            </div>-->
                        </footer>
                    </section-title>
                    <hr style="border-top: medium double #333;margin-bottom: 40px;">
                    <!--声明-->
                    <!--                    <div class="open-message">
                                            <p>声明：Gblog博客|版权所有，违者必究|如未注明，均为原创|本网站采用<a
                                                    href="https://creativecommons.org/licenses/by-nc-sa/3.0/" target="_blank">BY-NC-SA</a>协议进行授权
                                            </p>
                                            <p>转载：转载请注明原文链接 - <a href="/">看一遍闭着眼都会安装Lua了</a></p>
                                        </div>-->
                    <!--评论-->
                    <!--                    <div class="comments">
                                            <comment v-for="item in comments" :key="item.comment.id" :comment="item.comment">
                                                <template v-if="item.reply.length">
                                                    <comment v-for="reply in item.reply" :key="reply.id" :comment="reply"></comment>
                                                </template>
                                            </comment>
                                        </div>-->
                </article>
            </main>
        </div>
    </div>
</template>

<script>
    import Banner from '@/components/banner'
    import sectionTitle from '@/components/section-title'
    import comment from '@/components/comment'
    import menuTree from '@/components/menu-tree'
    import {fetchComment} from '../api'

    export default {
        name: 'articles',
        data() {
            return {
                showDonate: false,
                comments: [],
                menus: []
            }
        },
        components: {
            Banner,
            sectionTitle,
            comment,
            menuTree
        },
        methods: {
            getComment() {
                fetchComment().then(res => {
                    this.comments = res.data || []
                }).catch(err => {
                    console.log(err)
                })
            },
            fetchH(arr, left, right) {
                if (right) {
                    return arr.filter(item => item.offsetTop > left && item.offsetTop < right)
                } else {
                    return arr.filter(item => item.offsetTop > left)
                }
            },
            // 生成目录
            createMenus() {
                let arr = []
                for (let i = 6; i > 0; i--) {
                    let temp = []
                    let e = document.querySelector(".entry-content").querySelectorAll(`h${i}`)
                    for (let j = 0; j < e.length; j++) {
                        let child = this.fetchH(arr, e[j].offsetTop, (j + 1 === e.length) ? undefined : e[j + 1].offsetTop)
                        temp.push({
                            h: i,
                            title: e[j].innerText,
                            id: e[j].id,
                            offsetTop: e[j].offsetTop,
                            child
                        })
                    }
                    if (temp.length) {
                        arr = temp
                    }
                }
                this.menus = arr
            }
        },
        mounted() {
            this.createMenus()
        },
        created() {
            this.getComment()
        }
    }
</script>
<style scoped lang="less">
    .site-content {
        position: relative;

    .site-main {
        padding: 80px 0 0 0;
    }

    }
    #article-menus {
        position: sticky;
        top: 0;
        box-shadow: 0 2px 6px rgba(0, 0, 0, .1);
        border-radius: 3px;
        padding: 15px;
        width: 300px;
        transform: translateX(-120%) translateY(150px);
        font-size: 14px;
    }

    article.hentry {

    .entry-header {

    .entry-title {
        font-size: 23px;
        font-weight: 600;
        color: #000000;
        margin: 0.67em 0;

    &
    :before {
        content: "#";
        margin-right: 6px;
        color: #d82e16;
        font-size: 20px;
        font-weight: 600;
    }

    }

    hr {
        height: 1px;
        border: 0;
        background: #EFEFEF;
        margin: 15px 0;
    }

    .breadcrumbs {
        font-size: 14px;
        color: #D2D2D2;
        text-decoration: none;
        margin-bottom: 30px;
    }

    }

    .entry-content {
    }

    footer.post-footer {
        width: 100%;
        padding: 20px 10px;
        margin-top: 30px;
        height: 65px;
        position: relative;

    i {
        font-size: 18px;
        margin-right: 5px;
    }

    .post-like {
        float: right;
        margin: 7px 0 0 20px;
    }

    .post-share {
        float: right;
        list-style: none;
        margin-right: 20px;
    }

    .donate {
        float: left;
        line-height: 36px;
        border-radius: 100%;
        -webkit-border-radius: 100%;
        -moz-border-radius: 100%;
        border: 1px solid #2B2B2B;

    &
    :hover {
        border: 1px solid goldenrod;

    span {
        color: goldenrod;
    }

    }
    span {
        color: #2B2B2B;
        padding: 10px;
        position: relative;
        cursor: pointer;
    }

    .donate_inner {
        display: none;
        margin: 0;
        list-style: none;
        position: absolute;
        left: 80px;
        top: -40px;
        background: #FFF;
        padding: 10px;
        border: 1px solid #ddd;
        box-shadow: 0 2px 6px rgba(0, 0, 0, .08);
        border-radius: 3px;

    &
    .show {
        display: block;
    }

    li {
        float: left;
    }

    img {
        width: 100px;
    }

    p {
        text-align: center;
        font-size: 15px;
        color: #D2D2D2;
        line-height: 1rem;
    }

    }

    .donate_inner:after, .donate_inner:before {
        content: "";
        position: absolute;
        left: 0;
        bottom: 45%;
        margin-left: -8px;
        border-top: 8px solid transparent;
        border-bottom: 8px solid transparent;
        border-right: 8px solid #fff;
    }

    .donate_inner:before {
        left: -1px;
        border-right: 8px solid #ddd;
    }

    }

    .post-tags {
        margin: 7px 0 0 20px;
        float: left;
        text-transform: uppercase;

    a:hover {
        color: #ff6d6d;
    }

    }
    }
    .open-message {
        margin: 50px 0;
        position: relative;
        background: #2B2B2B;
        padding: 10px 30px;
        border-radius: 3px;
        font-size: 14px;
        color: #fff;

    &
    :after {
        content: "";
        border-left: 10px solid transparent;
        border-right: 10px solid transparent;
        border-bottom: 10px solid #2B2B2B;
        position: absolute;
        top: -8px;
        left: 48%;
    }

    p {
        margin: 10px 0;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    a {
        color: #A0DAD0;
        padding: 0 5px;
    }

    }
    }
</style>

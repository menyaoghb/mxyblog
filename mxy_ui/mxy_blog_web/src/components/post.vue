<template>
    <article class="post post-list">
      <div class="post-entry" style="width: 50%;">
        <h1 class="entry-title">
          <router-link target="_blank" :to="`/article/${post.id}`"><!--<span v-if="post.isTop" style="color:#ff6d6d;font-weight:600">[置顶] </span>--><span style="font-family: cursive;">{{post.title}}</span></router-link>
        </h1>
      </div>
      <div style="display: inline-block">
        <img height="70" width="120" :src="post.filePath">
      </div>
        <div class="post-entry" style="display: inline-block;width: 50%;position: absolute;">
          <router-link target="_blank" :to="`/article/${post.id}`"><p class="summary" :title="post.summary" style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;margin-top: 20px">{{post.summary}}</p></router-link>
            <p class="summary" style="font-size:xx-small;text-align: left;margin-top: 10px;"><span v-show="post.pageViews!==0"><i class="iconfont iconeyes"></i>    {{post.pageViews}}        </span><i class="iconfont iconmeditor-time"></i>    {{post.createTime | parseTime}}</p>
        </div>

    </article>
</template>

<script>

    export default {
        name: "post",
        props: {
            post: {
                type: Object
            }
        }
    }
</script>

<style scoped lang="less">
    .post {
        margin: 0 0 1% 0;
        position: relative;
    }

    .post-entry {
        .feature {
            position: absolute;
            margin-top: 10px;

            img {
                width: 100px;
                height: 100px;
                object-fit: cover;
                border-radius: 50%;
                padding: 2px;
                border: 1px solid #DADADA;
                position: relative;
                transition: all 0.2s linear;
                overflow: hidden;
                &:hover {
                    transform: scale(1.1, 1.1);
                    filter: contrast(130%);
                }
            }
        }

        .entry-title {
            font-size: 20px;
            font-weight: 300;
            line-height: 20px;
            margin: 0 0 1% 0;
            position: relative;
            z-index: 1;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            width: 100%;
            color: #000000;

            & a:hover {
                color: #4A9FF9;
            }
        }

        .p-time {
            position: absolute;
            right: 0;
            top: 16px;
            font-size: 12px;
            color: #989898;
            letter-spacing: 1px;
            font-family: din, 'Hiragino Sans GB', 'Microsoft Yahei', Arial, sans-serif;
            display: flex;
            align-items: center;
        }

        p.summary {
            min-height: 20px;
            margin: 0 0 0 2%;
            font-size: 12px;
            color: #6f6f6f;
            letter-spacing: 1px;
            line-height: 20px;
        }

        footer.entry-footer {
            margin: 0 0 0 17%;
            list-style: none;

            .post-more {
                margin-top: 10px;
                text-align: right;
                color: #A0DAD0;

                a:hover {
                    color: #737373;
                }
            }

            .info-meta {
                margin-top: 10px;
                font-family: din, 'Hiragino Sans GB', 'Microsoft Yahei', Arial, sans-serif;
                position: absolute;
                top: 20px;
                opacity: 0;
                padding-top: 8px;
                border-top: 1px solid #ddd;
                -webkit-transform: translate3d(-150px, 0, 0);
                transform: translate3d(-150px, 0, 0);
                visibility: hidden;
                transition: .7s all ease;
                -webkit-transition: .6s all ease;
                -moz-transition: .6s all linear;
                -o-transition: .6s all ease;
                -ms-transition: .6s all ease;

                span, a {
                    color: #B3B3B3;
                    font-size: 12px;
                }

                i {
                    margin-top: 3px;
                    margin-right: 10px;
                    float: left
                }
            }

            .comnum {
                float: left;
            }
        }

    }

    .post-entry:hover footer.entry-footer .info-meta {
        -webkit-transform: translate3d(-230px, 0, 0);
        transform: translate3d(-230px, 0, 0);
        opacity: 1;
        visibility: visible;
    }

    .post hr {
        width: 30%;
        height: 1px;
        margin: 0 auto;
        border: 0;
        background: #EFEFEF;
    }

    /*******/
    @media (max-width: 1060px) {
        .info-meta{
            display: none;
        }
    }
    @media (max-width: 800px) {
        .post-entry {
            .feature img {
                width: 50px;
                height: 50px;
            }
            .entry-title{
                font-size: 16px;
                line-height: 30px;
                margin: 0 0 0 65px;
            }
            .p-time {
                position: relative;
                margin: -15px 0 0 65px;
            }
            p.summary {
                margin: 20px 0 0 65px;
                font-size: 14px;
                height: 30px;
                overflow: hidden;
            }
            .post-more{
                display: none;
            }
        }
        .post-list hr {
            margin-top: 20px;
        }
    }

    /******/
</style>

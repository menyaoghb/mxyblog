<template>
    <div id="loading" v-show="loading" >
        <div class="loader">
            <div class="body-loading"></div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "loading",
        computed: {
            loading() {
                return this.$store.state.loading
            }
        },
        watch: {
            loading: {
                immediate: true,
                handler: (n, o) => {
                    let htmlBodyElement = document.querySelector('body');
                    htmlBodyElement.style.overflowY = n ? 'hidden' : 'auto';
                }
            }
        }
    }
</script>

<style scoped>
    /*loading开始*/
    .body-loading {
        width: 45px;
        height: 45px;
        display: block;
        animation: sunLoading 1s steps(12, end) infinite;
        background: transparent url('../style/img/loading.gif');
        background-size: 100%;
        margin:auto;
        position:absolute;
        top: 0;
        left: 0;
        bottom: 0;
        right:0;
    }
    /*loading结束*/

    #loading {
        position: fixed;
        z-index: 999;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #F9F9F9;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .loader {
        box-sizing: border-box;
        display: flex;
        flex: 0 1 auto;
        flex-direction: column;
        flex-grow: 1;
        flex-shrink: 0;
        flex-basis: 25%;
        max-width: 25%;
        height: 200px;
        align-items: center;
        justify-content: center;
    }

</style>
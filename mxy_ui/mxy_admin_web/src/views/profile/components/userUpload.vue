<template>
  <div>
    <el-upload
      :multiple="false"
      :show-file-list="false"
      ref="fileUpload"
      action="#"
      list-type="picture-card"
      :before-upload="beforeAvatarUpload"
      :http-request="uploadImg">
      <img v-if="imageUrl" :src="imageUrl" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
  </div>
</template>

<script>
import store from "@/store";
import {uploadPhoto} from "@/api/sys/user/user"
import axios from "axios";

export default {
  data() {
    return {
      imageUrl: store.getters.avatar,
      userId: store.getters.userId
    };
  },
  methods: {
    uploadImg(f) {
      let formData = new FormData()
      formData.append('file', f.file)
      formData.append('fileName', store.getters.nickName)
      formData.append('fileType', "用户头像")
      axios({
        url: window.SITE_CONFIG['systemUrl'] + '/api/foreign/uploadPicture',
        method: 'post',
        data: formData,
        headers: {'Content-Type': 'multipart/form-data'}
      }).then(res => {
        console.log("res", res)
        if (res.code === 200) {
          this.$message({
            message: '上传成功',
            type: 'success'
          });
        } else {
          this.$message({
            message: '上传失败',
            type: 'error'
          });
        }

      }).then(error => {
        console.log(error)
      })
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      // 调用上传头像接口
      let formData = new FormData();
      formData.append("userId", this.userId);
      formData.append("imageUrl", file.raw);
      uploadPhoto(formData).then(() => {
        this.$message({
          message: '修改成功',
          type: 'success'
        });
      })
    },
    beforeAvatarUpload(file) {
      const isJPG = 'image/jpeg/jpg/png/PNG/JPG/JPEG/IMAGE'.includes(file.type);
      const isLt2M = file.size / 1024 / 1024 < 5;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 image/jpeg/jpg/png 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 5MB!');
      }
      return isLt2M;
    }
  }
}
</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 148px;
  height: 148px;
  display: block;
}
</style>

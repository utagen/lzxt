<template>
  <section>
    <div class="breadcrumb-wrap exercise">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item>个人中心</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <named-header title="个人中心"></named-header>
    <div class="form-wrap profile-form">
      <div class="tips">提示：为了更好的服务您的学习，提高您的学习效率，请尽早完善你的个人资料</div>
      <el-form label-position="left"  label-width="120px" @submit.native.prevent="handleSubmit">
        <!--<el-form-item label="绑定微信:"></el-form-item>-->
        <el-form-item label="微信名称:">{{form.name}}</el-form-item>
        <el-form-item label="绑定手机号:">
          <el-input v-model="form.mobile" />
        </el-form-item>
        <el-form-item label="用户昵称:">
          <el-input v-model="form.nickname" :maxlength="6"/>
        </el-form-item>
        <el-form-item label="性别:">
          <el-radio-group v-model="form.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">女</el-radio>
            <el-radio :label="0">保密</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="所在地址:" class="el-form-item__address">
          <el-input class="el-input__address" v-model="form.province">
            <template slot="append">省</template>
          </el-input>
          <el-input class="el-input__address" v-model="form.city">
            <template slot="append">市</template>
          </el-input>
          <el-input class="el-input__address" v-model="form.area">
            <template slot="append">区/县</template>
          </el-input>
        </el-form-item>
        <el-form-item label="学校:">
          <el-input v-model="form.school"></el-input>
        </el-form-item>
        <el-form-item label="年级:"><grade-select v-model="form.grade" :enable-all="false"/></el-form-item>
        <el-form-item label="个人专属链接:" class="profile-invitee">
          <el-select v-model="posterId">
            <el-option v-for="item in posters" :label="item.name" :value="item.id" :key="item.id"></el-option>
          </el-select>
          <!--<el-input class="el-input__invitee_link" v-model="inviteUrl" readonly disabled></el-input>-->
          <el-button type="primary" class="invitee-button" @click="handleCreateUserPoster">生成海报</el-button>
        </el-form-item>
        <el-form-item label-width="0">
          <div class="button-group">
            <el-button @click="$router.go(-1)">返回</el-button>
            <el-button type="primary" native-type="submit">确认</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <el-drawer :visible.sync="showUserPoster" custom-class="user-poster-drawer" :with-header="false" size="600px" append-to-body>
      <poster :url="inviteUrl" />
    </el-drawer>
  </section>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
  name: 'user-profile',
  data () {
    const profile = this.$store.state.user.profile
    return {
      form: {
        nickname: profile.nickname,
        sex: profile.sex,
        name: profile.name,
        mobile: profile.mobile,
        school: profile.school,
        grade: profile.grade,
        province: profile.province,
        city: profile.city,
        area: profile.area
      },
      inviteUrl: profile.inviteUrl,
      posterId: '',
      showUserPoster: false
    }
  },
  computed: {
    ...mapState('user', ['posters'])
  },
  async mounted () {
    const list = await this.getPosterList()
    if (list.length > 0) { this.posterId = list[0].id }
  },
  methods: {
    ...mapActions('user', ['createInviteeLink', 'updateProfile', 'getPosterList']),
    handleGenInviteeLink () {
      this.createInviteeLink().then(url => { this.inviteUrl = url })
    },
    handleSubmit () {
      this.updateProfile({ ...this.form }).then(() => {
        this.$notify.success('更新成功')
      })
    },
    handleCreateUserPoster () {
      if (this.posterId) {
        this.createInviteeLink(this.posterId).then(inviteUrl => {
          this.inviteUrl = inviteUrl
          this.showUserPoster = true
        })
      }
    }
  }
}
</script>

<style lang="scss">
@import '~@/styles/vars.scss';

.user-poster-drawer {
  .el-drawer__body {
    height: 100%;
    width: 100%;
    background-color: $--color-black;
  }
}
</style>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.tips {
  color:  $--color-info;
  text-align: center;
  margin-bottom: 20px;
}

.profile-form {
  .el-input,
  .el-select {
    width: 200px;
  }

  .el-input__address {
    width: 140px;
    margin-right: 10px;
  }
  .el-input__invitee_link {
    width: 400px;
  }

  .el-button {
    width: 80px;
  }

  .invitee-button {
    margin-left: 10px;
  }
  .button-group {
    margin-top: 50px;
    text-align: center;

    .el-button {
      margin-left: 150px;

      &:first-child {margin-left: 0;}
    }

  }
}

</style>

<style lang="scss" scoped>
@media screen and (max-width: 810px) {
  .profile-form {
    padding: 50px;
  }
}
@media screen and (max-width: 710px) {
  .profile-form {
    padding: 20px;
  }
}

@media screen and (max-width: 650px) {
  .el-input__address {
    margin-bottom: 10px;
  }
  .el-form-item__address {
    margin-bottom: 8px;
  }
}

@media screen and (max-width: 500px) {
  .profile-form {
    .el-input__address { width: 100%; margin-right: 0;}
  }
}
</style>

<style lang="scss">
@media screen and (max-width: 500px) {
  .profile-form {
    .el-form-item {
      .el-form-item__label {
        float: none;
        display: block;
        width: 100% !important;
        margin-bottom: 5px;
      }

      .el-form-item__content {
        margin-left: 0!important;
      }

      .button-group {
        margin-top: 20px;
        .el-button {
          margin-left: 10%;
        }
      }
    }

    .profile-invitee {
      .el-form-item__content { display: flex;}
    }
  }

}

</style>

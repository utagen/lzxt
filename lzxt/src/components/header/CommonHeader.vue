<template>
  <header class="header container">
    <div class="logo">
      <router-link to="/">
        <img src="@/images/logo.png" alt="励志学堂" class="logo-l"/>
        <img src="@/images/logo-s.png" alt="励志学堂" class="logo-s"/>
      </router-link>
    </div>
    <div class="nav"><slot name="nav"/></div>
    <el-dropdown trigger="click" v-if="mobile" @command="handleCommandSelected" class="profile-mobile-dropdown">
      <span  v-if="isAuthenticated">
        <el-avatar :src="user.headImageUrl" :alt="user.nickname" :size="30" fit="contain"/>
        <span class="user-name">{{user.nickname}}</span>
      </span>
      <span v-else><i class="lzxt-icon lzxt-icon-list"/></span>
      <el-dropdown-menu>
        <template v-if="isAuthenticated">
          <el-dropdown-item command="/user/profile">个人信息</el-dropdown-item>
          <el-dropdown-item v-if="isLayout1" command="/courses">进入学习</el-dropdown-item>
          <el-dropdown-item divided command="/user/logout">退出</el-dropdown-item>
        </template>
        <template v-else>
          <el-dropdown-item command="/user/login">登录</el-dropdown-item>
          <el-dropdown-item command="/courses">免费试用</el-dropdown-item>
        </template>
      </el-dropdown-menu>
    </el-dropdown>
    <ul class="profile" v-else>
      <template v-if="isAuthenticated">
        <li class="profile-item">
          <el-dropdown @command="$router.push($event)" class="profile-dropdown">
            <span>
              <el-avatar :src="user.headImageUrl" :alt="user.nickname" :size="40" fit="contain"/>
              <span class="user-name">{{user.nickname}}</span>
            </span>
            <el-dropdown-menu>
              <el-dropdown-item command="/user/profile">个人信息</el-dropdown-item>
              <el-dropdown-item divided command="/user/logout">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown >
        </li>
        <li class="profile-item" v-if="isLayout1">
          <router-link to="/courses"><i class="lzxt-icon lzxt-icon-go-in"/><span>进入学习</span></router-link>
        </li>
        <li class="profile-item" v-else>
          <router-link to="/user/logout"><span>退出</span><i class="lzxt-icon lzxt-icon-logout"/></router-link>
        </li>
      </template>
      <template v-else>
        <li class="profile-item logout">
          <router-link slot="reference" to="/user/login" ><i class="lzxt-icon lzxt-icon-user" /><span>登录</span></router-link>
        </li>
        <li class="profile-item">
          <router-link to="/courses"><i class="lzxt-icon lzxt-icon-pen"/><span>免费试用</span></router-link>
        </li>
      </template>
    </ul>
  </header>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'common-header',
  props: {
    layout: {
      type: String,
      default: 'layout-1'
    }
  },
  data () {
    return { mobile: false, debounceTimer: 0 }
  },
  mounted () {
    this.getProfile()
    this.relayout()
    window.addEventListener('resize', this.debounce)
  },
  computed: {
    ...mapState('user', {
      isAuthenticated: 'isAuthenticated',
      user: 'profile',
      loginQRCodeUrl: 'loginQRCodeUrl'
    }),
    isLayout1 () {
      return this.layout === 'layout-1'
    },
    isLayout2 () {
      return this.layout === 'layout-2'
    }
  },
  methods: {
    ...mapActions('user', ['getProfile']),
    debounce () {
      if (this.debounceTimer) {
        clearTimeout(this.debounceTimer)
      }
      this.debounceTimer = setTimeout(() => {
        this.relayout()
      }, 500)
    },
    handleCommandSelected (command) {
      this.$router.push(command)
    },
    relayout () {
      this.mobile = document.documentElement.clientWidth <= 499
    }
  },
  beforeDestroy () {
    window.removeEventListener('resize', this.debounce)
  }
}
</script>

<style lang="scss" scoped>
@import "~@/styles/vars.scss";
@import "~@/styles/mixins.scss";

.header {
  display: flex;
  padding-top: 38px;
  height: 112px;
  position: relative\9;
}

.logo {
  position: absolute\9;
  width: 260px;
  min-width: 80px;

  a {display: block;}

  img {
    max-width: 100%;
    vertical-align: middle;
  }
}

.logo-s {
  display: none;
}

.nav {
  margin-left: 260px\9;
  margin-right: 250px\9;
  flex: 1;
}

.profile {
  position: absolute\9;
  bottom: 0\9;
  right: 0\9;
  margin: 0;
  display: inline-block;
  padding: 10px 20px;
  min-width: 98px;

  .profile-item {
    display: inline-block;
    padding-left: 30px;
    line-height: 40px;
    font-size: $--font-size-medium;

    &:first-child {
      padding-left: 0;
    }

    .el-avatar {
      margin: 0 8px;
      vertical-align: middle;
      border: $--border-base;
    }
  }
  .lzxt-icon {
    vertical-align: middle;
    margin: 0 8px;
    font-size: 18px;
    font-weight: 900;
    color: $--color-warning;
  }
}
.el-dropdown-menu {
  font-size: $--font-size-medium;
}

.profile-dropdown {
  font-size: $--font-size-medium;
}

.profile-mobile-dropdown {
  padding: 10px;
  line-height: 30px;
  font-size: $--font-size-medium;
}

</style>

<style lang="scss" scoped>
@media screen and (max-width: 768px){
  .logo {
    position: relative;
    width: 180px;

    a {display: block; position: absolute; top: 50%; transform: translateY(-50%);}
  }
  .header { padding-top: 0; height: 74px;}

  .profile {
    .profile-item {
      padding-left: 20px;
      font-size: 14px;

      .lzxt-icon { display: none; }
    }
  }
  .nav {padding-left: 0;}
}

@media screen and (max-width: 585px) {
  .profile {
    padding: 20px 10px;
    .profile-item {
      padding-left: 10px;

      span { display: none;}
      .lzxt-icon { display: inline-block;}

    }
  }
}

@media screen and (max-width: 499px) {

  .logo {
    width: 80px;
    height: 50px;
    padding-left: 10px;
    .logo-l {
      display: none;
    }

    .logo-s {
      width: 40px;
      display: block;
    }
  }

  .header {
    height: 50px;
  }
}

@media screen and (max-width: 420px) {
  .user-name { display: none;}
}
</style>

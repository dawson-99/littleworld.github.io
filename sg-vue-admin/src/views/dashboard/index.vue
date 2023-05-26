<template>
  <div class="dashboard-container">
    <div class="dashboard-text">
      成都理工博客管理系统
    </div>
    <div class="bottom">
      欢迎关注我的B站账号：<a href="https://space.bilibili.com/1162150122/video">我的可乐呢</a>   
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>

.dashboard-container{

  font-size: 20px;
  margin: 50px;
  .bottom{
    margin-top: 20px;
  }
}

</style>

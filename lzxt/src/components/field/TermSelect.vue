<template>
  <el-select v-bind="$attrs" v-on="$listeners">
    <el-option v-for="(item, index) in tearms" :label="item" :value="item"  :key="index" />
  </el-select>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'term-select',
  computed: {
    ...mapGetters('field', {
      tearms: 'conditionTerms'
    })
  }
}
</script>

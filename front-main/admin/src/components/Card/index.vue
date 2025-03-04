<template>
  <div class="module">
    <div class="title">{{ title }}</div>
    <slot />
  </div>
</template>

<script>
export default {
  name: 'Card',
  props: {
    title: {
      type: String,
      default: ''
    }
  }
}
</script>

<style scoped>
</style>

function scrollToTop (routerTop = false) {
  let offsetTop = 0
  if (routerTop) {
    const $header = document.querySelector('.header')
    if ($header) {
      offsetTop += $header.clientHeight
    }
    const $banner = document.querySelector('.header')
    if ($header) {
      offsetTop += $banner.clientHeight
    }
  }
  window.scroll(0, offsetTop)
}

export { scrollToTop }

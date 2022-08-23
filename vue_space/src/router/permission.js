import router from "@/router/index";

router.beforeEach((to,from,next) => {
    let temp = localStorage.getItem("authority")
    if (to.path === '/login') {
        if (localStorage.getItem("token") != null) {
            next({
                path: '/home'
            })
        }else {
            next()
        }
    }else if (to.path === '/register' || to.path === '/404' || to.path === '/401') {
        next()
    } else {
        if (localStorage.getItem("token") == null) {
            next({
                path: '/login'
            })
        }else {
            if (to.path === '/employee') {
                if ((temp & (1 << 1)) > 0) {
                    next()
                }else {
                    next({
                        path: '/401'
                    })
                }
            } else if (to.path === '/pInformation') {
                if ((temp & (1 << 2)) > 0) {
                    next()
                }else {
                    next({
                        path: '/401'
                    })
                }
            } else if (to.path === '/authority') {
                if ((temp & (1 << 3)) > 0) {
                    next()
                }else {
                    next({
                        path: '/401'
                    })
                }
            }else if (to.path === '/home' || to.path === '/' ) {
                next()
            }else {
                next({
                    path: '/404'
                })
            }
        }
    }
})

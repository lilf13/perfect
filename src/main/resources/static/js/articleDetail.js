/* 获取发布评论按钮对象 */
let commentPostingButton = $("#commentPostingButton")
/* 获取发布评论框对象 */
let commentTextarea = $("#commentTextarea")
/* 获取评论发布情况显示div */
let successfullyPostedCommentsDiv = $("#successfullyPostedCommentsDiv")
let commentP = $("#commentP")

commentPostingButton.on("click", function () {
    /* 未登录时点击弹出登录框 */
    if ($("#headPortraitImg").attr("src") == '../../../../img/localImg/login.png') {
        /* 显示笼罩div和登录框 */
        $("#shroudedInDiv").css('display', 'block')
        $("#loginDiv").css('display', 'block')
        // 滑轮消失
        $("body").css('overflow', 'hidden')
    } else {
        if (commentTextarea.val() === "") {
            successfullyPostedCommentsDiv.css('display', 'flex')
            commentP.html("请填写评论内容")
            setTimeout(function () {
                successfullyPostedCommentsDiv.css('display', 'none')
                commentP.html("")
            }, 1200)
        } else {
            let formData = new FormData()
            formData.append("parentComId",0)
            formData.append("comment", commentTextarea.val())
            fetch('/forum/com/release', {
                method: 'POST',
                body: formData
            })
            .then(response => {
                if (response.ok) {
                    commentTextarea.val("")
                    successfullyPostedCommentsDiv.css('display', 'flex')
                    commentP.html("评论成功，审核后显示")
                    setTimeout(function () {
                        successfullyPostedCommentsDiv.css('display', 'none')
                        commentP.html("")
                    }, 1800)
                } else {
                    // 请求失败处理
                }
            })
        }
    }
})

/* 获取加载更多评论按钮对象 */
let allCommentsBut = $("#allCommentsBut")
/* 获取评论显示中心div对象 */
let commentDisplayCenter = $("#commentDisplayCenter")
/* 获取笼罩div对象 */
let shroudedInDiv = $("#shroudedInDiv")
allCommentsBut.on("click", function () {
    /* 未登录时点击弹出登录框 */
    if ($("#headPortraitImg").attr("src") == '../../../../img/localImg/login.png') {
        /* 显示笼罩div和登录框 */
        $("#shroudedInDiv").css('display', 'block')
        $("#loginDiv").css('display', 'block')
        // 滑轮消失
        $("body").css('overflow', 'hidden')
    } else {
        // 使用 jQuery 动画滚动到顶部
       /* $("#commentDisplayCenter").animate({ scrollTop: 0 }, 'slow');*/
        /*回复评论最初状态*/
        /*$("#commentBodyDivFramework").html(allCommentsOfHtml)*/
        commentDisplayCenter.css("display", "block")
        // 滑轮消失
        $("body").css("overflow", "hidden")
        // 笼罩div出现
        shroudedInDiv.css("display", "block")
    }
})

/* 获取关闭窗口叉图标 */
let forkIcon = $("#forkIcon")
forkIcon.on("click", function () {
    commentDisplayCenter.css("display", "none")
    // 滑轮出现
    $("body").css("overflow", "auto")
    // 笼罩div消失
    shroudedInDiv.css("display", "none")
})

/* 点赞事件 */
function like(commentIndex, commentId, blogId) {
    let likeIconId = "likeIcon_" + commentId
    let likeIconIdObj = $("#" + likeIconId)
    // 获取评论数font对象
    let likeCount = $("#likeCount_" + commentId)
    let formData = new FormData()
    formData.append("commentIndex", commentIndex)
    formData.append("commentId", commentId)
    formData.append("blogId", blogId)
    fetch("/blog/commentProcessing/likeAction", {
        method: 'POST',
        body: formData
    })
    .then(response => response.text())
    .then(data => {
        // 点赞成功
        if (data === "1") {
            likeIconIdObj.css('fill', '#c15730')
            likeCount.html(parseInt(likeCount.html()) + 1)
        } else if (data === "0") {
            likeIconIdObj.css('fill', '')
            likeCount.html(parseInt(likeCount.html()) - 1)
        }
    })
}

/* 回复框弹出事件 */
function replyBoxPopUp(index) {
    // 获取‘回复’文字对象
    let replyText = $("#replyText_" + index)
    let commentPostingByCenterDiv = $("#commentPostingByCenterDiv_" + index)
    // 先判断当前回复框弹出状态
    if (replyText.val() == '回复') {
        replyText.val('收起')
        commentPostingByCenterDiv.css('display', 'block')
    } else {
        replyText.val('回复')
        commentPostingByCenterDiv.css('display', 'none')
    }
}

/*回复提交事件*/
var countStr = ''
function replySubmission(index) {
    //获取回复框对象
    let commentPostingTextarea = $("#commentPostingTextarea_" + index)
    if (commentPostingTextarea.val() == '') {
        commentPostingTextarea.val('')
        successfullyPostedCommentsDiv.css('display', 'flex')
        commentP.html("请输入内容")
        setTimeout(function () {
            successfullyPostedCommentsDiv.css('display', 'none')
            commentP.html("")
        }, 1800)
    } else {
        let formData = new FormData()
        formData.append("parentComId",index)
        formData.append("comment",commentPostingTextarea.val())
        fetch('/forum/com/release', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    commentPostingTextarea.val('')
                    successfullyPostedCommentsDiv.css('display', 'flex')
                    commentP.html("回复成功，审核后显示")
                    setTimeout(function () {
                        successfullyPostedCommentsDiv.css('display', 'none')
                        commentP.html("")
                    }, 1800)
                } else {
                    // 请求失败处理
                }
            })


}
}

/*展开回复事件*/
function expandReply(index) {
    //获取回复展示div对象
    let personalCommentReplyDiv = $("#personalCommentReplyDiv_" + index)
    //获取回复展开按钮对象
    let expandCommentsBut = $("#expandCommentsBut_" + index)
    // 定义正则表达式
    let pattern = /^展开(\d+)条回复▼$/;
    if (expandCommentsBut.val().match(pattern)) {
        countStr = expandCommentsBut.val()
        fetch('/forum/com/reply/' + index, {
            method: 'GET',
        })
            .then(response => {
                if (response.ok) {
                    return response.json()
                } else {
                    throw new Error('Network response was not ok');
                }
            })
            .then(jsonObj => {
                console.log(jsonObj)
                let replyHtmlStr = ''
                for (var i=0;i<jsonObj.length;i++) {
                    // 处理时间格式
                    const dateString = jsonObj[i].releaseDate;
                    const formattedString = dateString.replace(/:\d+\.\d+$/, ':03');
                    replyHtmlStr += `
                       <div class="commentResponseDisplayDiv">
                           <div class='commentResponseImgDiv'>
                               <img class='commentResponseImg' src='${jsonObj[i].user.userDetail.avatarPath}'>
                           </div>
                           <div class='commentatorAndTimeSpanByPersonCom'>
                               <font style='color: #666;font-size: 15px'>${jsonObj[i].user.userDetail.userName}</font><font style='font-weight: bold;color: #666;font-size: 15px;font-style: italic'>回复道&nbsp;—&nbsp;</font>
                               <font style="position: relative;top: 1px;font-size: 14px;color: #333333;font-family: 'Microsoft JhengHei Light'">${formattedString}</font>
                               <span class="replyToCommentsAndLikesSpan">
                                    <svg style="fill: #383635" t='1685277063628' class='likeIcon' onclick="like('1')" viewBox='0 0 1024 1024' version='1.1' xmlns='http://www.w3.org/2000/svg' p-id='3381' width='200' height='200'><path d='M857.28 344.992h-264.832c12.576-44.256 18.944-83.584 18.944-118.208 0-78.56-71.808-153.792-140.544-143.808-60.608 8.8-89.536 59.904-89.536 125.536v59.296c0 76.064-58.208 140.928-132.224 148.064l-117.728-0.192A67.36 67.36 0 0 0 64 483.04V872c0 37.216 30.144 67.36 67.36 67.36h652.192a102.72 102.72 0 0 0 100.928-83.584l73.728-388.96a102.72 102.72 0 0 0-100.928-121.824zM128 872V483.04c0-1.856 1.504-3.36 3.36-3.36H208v395.68H131.36A3.36 3.36 0 0 1 128 872z m767.328-417.088l-73.728 388.96a38.72 38.72 0 0 1-38.048 31.488H272V476.864a213.312 213.312 0 0 0 173.312-209.088V208.512c0-37.568 12.064-58.912 34.72-62.176 27.04-3.936 67.36 38.336 67.36 80.48 0 37.312-9.504 84-28.864 139.712a32 32 0 0 0 30.24 42.496h308.512a38.72 38.72 0 0 1 38.048 45.888z' p-id='3382'></path></svg>
                                    <font>0</font>
                               </span>
                           </div>
                           <div class='commentDetailDivByPersonCom'>
                               ${jsonObj[i].content}
                           </div>
                      </div>`;
                }
                personalCommentReplyDiv.html(replyHtmlStr)
                //改变展开按钮内容
                expandCommentsBut.val('收起▲')
            })
    } else {
        //收起展开区
        personalCommentReplyDiv.html('')
        expandCommentsBut.val(countStr)
    }


}

// 使用 JavaScript 事件监听器来监听整个页面的鼠标点击事件
$(document).on('click', function(event) {
    // 如果已经打开全部评论窗口
    if ($('#commentDisplayCenter').css('display') === 'block') {
        if (!(event.clientX > 380 && event.clientX < 1153 && event.clientY > 35 && event.clientY < 710)) {
            $('#commentDisplayCenter').css("display", "none");
            // 滑轮出现
            $("body").css("overflow", "auto");
            // 笼罩div消失
            $('#shroudedInDiv').css("display", "none");
        }
    }
})

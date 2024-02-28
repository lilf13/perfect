/*富文本编辑器*/
const { createEditor, createToolbar } = window.wangEditor
const editorConfig = {
    maxLength: 3000,
    customPaste: (editor, event) => {
        const maxLength = 3000; // 设置最大字数限制为 3000
        // 判断粘贴的类型是文本还是图像
        const isText = event.clipboardData.types.includes('text/plain');
        if (isText) {
            const text = event.clipboardData.getData('text/plain');
            if (text.length > maxLength) {
                // 截取超出限制的部分
                const truncatedText = text.substring(0, maxLength);
                // 在编辑器中插入截取后的文本
                editor.insertText(truncatedText);
            } else {
                // 在编辑器中插入完整的粘贴文本
                editor.insertText(text);
            }
            // 阻止默认的粘贴行为
            event.preventDefault();
            return false;
        }
        // 如果粘贴的是图像，则继续执行默认的粘贴行为
        return true;
    },
    MENU_CONF: {
        'uploadImage': {
            server: '/forum/blog/articleImg',
            maxFileSize: 1024 * 1024 * 100 // 设置图片大小限制
        }
    }
}
const editor = createEditor({
    selector: '#editor-container',
    html: '<p><br></p>',
    config: editorConfig,
    mode: 'iframe', // 修改为 iframe
})

const toolbarConfig = {}
const toolbar = createToolbar({
    editor,
    selector: '#toolbar-container',
    config: toolbarConfig,
    mode: 'iframe', // 修改为 iframe
})
$(document).ready(function() {
    $(window).on('scroll', function() {
        const container = $("#toolbar-container");
        if ($(window).scrollTop() >= 104.80000305175781) {
            container.css({
                position: 'fixed',
                top: '0',
                width: '100%'
            });
        } else {
            container.css({
                position: '',
                top: '',
                width: ''
            });
        }
    });

    const maxTags = 3;
    let selectedTags = [];
    $('.tag').on('click', function() {
        const tag = $(this);
        if (tag.hasClass('selected')) {
            tag.removeClass('selected');
            selectedTags = selectedTags.filter(selectedTag => selectedTag !== tag.text());
        } else if (selectedTags.length < maxTags) {
            tag.addClass('selected');
            selectedTags.push(tag.text());
        }
    });

    const selectTipsDiv = $("#selectTipsDiv");
    const selectTag = $("#selectTag");
    selectTag.on('mouseover', function() {
        selectTipsDiv.css('visibility', 'visible');
    });
    selectTag.on('mouseout', function() {
        selectTipsDiv.css('visibility', 'hidden');
    });

    const introduceTipsDiv = $("#introduceTipsDiv");
    const briefIntroductionIcon = $("#briefIntroductionIcon");
    briefIntroductionIcon.on('mouseover', function() {
        introduceTipsDiv.css('visibility', 'visible');
    });
    briefIntroductionIcon.on('mouseout', function() {
        introduceTipsDiv.css('visibility', 'hidden');
    });

    const typeTipsDiv = $("#typeTipsDiv");
    const typeIcon = $("#typeIcon");
    typeIcon.on('mouseover', function() {
        typeTipsDiv.css('visibility', 'visible');
    });
    typeIcon.on('mouseout', function() {
        typeTipsDiv.css('visibility', 'hidden');
    });

    const releaseButObj = $("#releaseBut");
    const vacancyReminderDiv = $("#vacancyReminderDiv");
    const vacancyReminderSpan = $("#vacancyReminderSpan");

    releaseButObj.on('click', function() {
        let count = 1;
        const title = $("#title").val();
        const textArea = $("#textArea").val();
        const type = $('input[name="type"]:checked');

        if (title.length < 5 || title.length > 50) {
            vacancyReminderDiv.css('display', 'flex');
            vacancyReminderSpan.html('标题长度应在5~50之间');
            setTimeout(function() {
                vacancyReminderDiv.css('display', 'none');
            }, 1200);
            count--;
        } else if (editor.getHtml().replace(/<p>|<\/p>|<br>/gi, '') === '') {
            vacancyReminderDiv.css('display', 'flex');
            vacancyReminderSpan.html('请填写正文');
            setTimeout(function() {
                vacancyReminderDiv.css('display', 'none');
            }, 1200);
            count--;
        } else if (selectedTags.length === 0) {
            vacancyReminderDiv.css('display', 'flex');
            vacancyReminderSpan.html('请选择标签');
            setTimeout(function() {
                vacancyReminderDiv.css('display', 'none');
            }, 1200);
            count--;
        } else if (textArea.length < 30 || textArea.length > 100) {
            vacancyReminderDiv.css('display', 'flex');
            vacancyReminderSpan.html('简介长度应在30~100之间');
            setTimeout(function() {
                vacancyReminderDiv.css('display', 'none');
            }, 1200);
            count--;
        } else if (type.length === 0) {
            vacancyReminderDiv.css('display', 'flex');
            vacancyReminderSpan.html('请选择博客类型');
            setTimeout(function() {
                vacancyReminderDiv.css('display', 'none');
            }, 1200);
            count--;
        }

        if (count === 1) {
            const formData = new FormData();
            const blob = new Blob([editor.getHtml()], { type: 'text/plain' });
            formData.append('title', title);
            formData.append('text', blob);
            formData.append('label', selectedTags);
            formData.append('briefIntroduction', textArea);
            formData.append('type', type.val());

            $.ajax({
                url: '/forum/blog/storage',
                method: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function(data) {
                    if ('true' === data) {
                        window.location.href = '/forum/view/success'
                    }
                },
            });

        }
    });
});

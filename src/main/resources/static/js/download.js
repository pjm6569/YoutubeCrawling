function downloadComments() {
    var userConfirmation = confirm('댓글 내용을 엑셀로 다운로드하시겠습니까?');
    var comments = [];
    $("#commentBody tr").each(function () {
        var comment = {
            authorProfileImage: $(this).find('td:eq(0) img').attr('src'),
            authorDisplayName: $(this).find('td:eq(0) span').text(),
            textOriginal: $(this).find('td:eq(1)').text(),
            publishedAt: $(this).find('td:eq(2)').text(),
            likeCount: parseInt($(this).find('td:eq(3)').text(), 10)
        };
        comments.push(comment);
    });
    if (userConfirmation) {
        $.ajax({
            type: "POST",
            url: "download",
            contentType: "application/json",
            data: JSON.stringify(comments),
            xhrFields: {
                responseType: 'blob'
            },
            success: function(blob) {
                var link = document.createElement('a');
                link.href = window.URL.createObjectURL(blob);
                link.download = "comments.xlsx";
                link.click();
                console.log("다운로드 성공");
            },
            error: function (error) {
                console.error("다운로드 오류", error);
            }
        });
    }
    else {
        return false;
    }
}

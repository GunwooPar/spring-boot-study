
// 댓글 업데이트
async function updateComment(postId, commentId, content, userId) {
    // 이 객체는 데이터를 key=value&key2=value2 형식으로 자동으로 변환해줌(@RequestParam이 가장 받기 좋아하는 형식)
    const formData = new URLSearchParams();
    formData.append('content', content);
    formData.append('userId', userId);

    const response = await fetch(
        `/board/${postId}/comments/${commentId}`,
        {
            method: 'PUT',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: formData
        }
    );

    if (!response.ok) {
        throw new Error('수정 실패');
    }

    const updatedCommentHtml = await response.text(); // 수정된 댓글 HTML 받음

    // 기존 댓글을 새 HTML로 교체
    const commentElement = document.querySelector(`[data-comment-id="${commentId}"]`);
    commentElement.outerHTML = updatedCommentHtml;
}

// 댓글 개수 업데이트
function updateCommentCount() {
    const count = document.querySelectorAll('.comment').length;
    document.getElementById('comment-count').textContent = count;
}

// 댓글 로딩
async function loadComments(postId) {
    const response = await fetch(`/board/${postId}/comments`);
    const commentsHtml = await response.text(); // JSON이 아닌 HTML 텍스트

    // 받은 HTML을 통째로 삽입
    document.getElementById('comments-list').innerHTML = commentsHtml;

    // 댓글 개수 업데이트
    updateCommentCount();
}

// 댓글 작성
async function createComment(postId, content, userId) {
    const formData = new URLSearchParams();
    formData.append('content', content);
    formData.append('userId', userId);

    const response = await fetch(`/board/${postId}/comments`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData
    });

    const newCommentHtml = await response.text(); // 새 댓글 HTML만 받음

    // 댓글 목록 맨 아래에 추가
    document.getElementById('comments-list').insertAdjacentHTML('beforeend', newCommentHtml);

    // 댓글 입력창 초기화
    document.getElementById('comment-content').value = '';

    // 댓글 개수 업데이트
    updateCommentCount();
}



async function deleteComment(postId, commentId, userId) {
    if (!confirm('정말 삭제하시겠습니까?')) return;

    try {
        const response = await fetch(
            `/board/${postId}/comments/${commentId}?userId=${userId}`,
            { method: 'DELETE' }
        );

        if (response.ok) {
            // DOM에서 댓글 제거
            const commentElement = document.querySelector(`[data-comment-id="${commentId}"]`);
            commentElement.remove();

            // 댓글 개수 업데이트
            updateCommentCount();
        } else {
            throw new Error('삭제 실패');
        }
    } catch (error) {
        console.error('댓글 삭제 실패:', error);
        alert('댓글 삭제에 실패했습니다.');
    }
}


function editComment(commentId) {
    const commentElement = document.querySelector(`[data-comment-id="${commentId}"]`);
    const contentElement = commentElement.querySelector('.comment-content');
    const currentContent = contentElement.textContent;

    // 기존 내용을 textarea로 교체
    contentElement.innerHTML = `
        <textarea class="edit-textarea" rows="3">${currentContent}</textarea>
        <button onclick="saveEdit(${commentId})">저장</button>
        <button onclick="cancelEdit(${commentId}, '${currentContent.replace(/'/g, "\\'")}')">취소</button>
    `;
}

async function saveEdit(commentId) {
    const commentElement = document.querySelector(`[data-comment-id="${commentId}"]`);
    const textarea = commentElement.querySelector('.edit-textarea');
    const newContent = textarea.value;
    const postId = getPostIdFromUrl(); // URL에서 postId 추출
    const userId = document.getElementById('user-id').value;

    try {
        await updateComment(postId, commentId, newContent, userId);
    } catch (error) {
        console.error('댓글 수정 실패:', error);
        alert('댓글 수정에 실패했습니다.');
    }
}

function cancelEdit(commentId, originalContent) {
    const commentElement = document.querySelector(`[data-comment-id="${commentId}"]`);
    const contentElement = commentElement.querySelector('.comment-text');
    contentElement.innerHTML = `<p>${originalContent}</p>`;
}



// URL에서 postId 추출
function getPostIdFromUrl() {
    const pathParts = window.location.pathname.split('/');
    return pathParts[pathParts.indexOf('board') + 1];
}

// 에러 처리
function handleError(error, message) {
    console.error(error);
    alert(message || '오류가 발생했습니다.');
}


document.addEventListener('DOMContentLoaded', function() {
    const commentForm = document.getElementById('comment-form');

    commentForm.addEventListener('submit', async function(e) {
        e.preventDefault(); // 폼 기본 제출 방지

        const content = document.getElementById('comment-content').value;
        const userId = document.getElementById('user-id').value;
        const postId = getPostIdFromUrl();

        try {
            await createComment(postId, content, userId);
        } catch (error) {
            console.log('댓글 작성 실패:', error);
            handleError(error, '댓글 작성에 실패했습니다.');
        }
    });
});


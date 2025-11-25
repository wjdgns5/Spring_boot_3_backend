// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if(deleteButton) {

    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;

        fetch(`/api/articles/${id}`, {
            method: 'DELETE'
        })
        .then( ()=> {
            alert('삭제가 완료되었습니다.');
            location.replace('/articles');
            });
        });
};

// 수정 가능
const modifyButton = document.getElementById('modify-btn');

if(modifyButton) {

    modifyButton.addEventListener('click', event => {

     let params = new URLSearchParams(location.search);
     let id = params.get('id');

    fetch(`/api/articles/${id}`, {
        method: 'PUT',
        headers: {
            "Content-type": "application/json",
        }, // end of headers

        body: JSON.stringify ({
               title: document.getElementById('title').value,
               content: document.getElementById('content').value
            })
    }) // end of fetch
    .then( () => {
        alert('수정이 완료되었습니다.');
        location.replace(`/articles/${id}`);
        // replace : 뒤로 가기(Back)를 누르면 방금 페이지로 안 돌아감
        });

    });
}

// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        fetch('/api/articles', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
            .then(() => {
                alert('등록 완료되었습니다.');
                location.replace('/articles');
            });
    });
}
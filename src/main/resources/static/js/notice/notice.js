//(질문,답변)삭제버튼클릭시 호출
  const delElements = document.getElementsByClassName("delete");
  Array.from(delElements).forEach(function (element) {

    //$(".delete").on("click",function(){ alert("정말삭제할거여?"); });
    element.addEventListener("click", function () {
      if (confirm("삭제하시겠습니까??")) {//확인버튼을 클릭하면
        location.href = this.dataset.uri;
      };
    });
  });
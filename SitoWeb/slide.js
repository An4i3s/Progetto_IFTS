$(document).ready(function() {
  var carousel = $(".carousel"),
      items = $(".item"),
      currdeg = 0,
      intervalId = null,
      rotationInProgress = false;

  
  function onBoxEnter() {
      $(this).css("transform", "scale(1.1)");
  }

  
  function onBoxLeave() {
      $(this).css("transform", "scale(1)");
  }

  
  $(".box").on("mouseenter", onBoxEnter);
  $(".box").on("mouseleave", onBoxLeave);

  $(".carousel").on("mousedown", function() {
      $(".box").off("mouseenter mouseleave");
  });

  carousel.on("mouseup", function() {
      
      $(".box").on("mouseenter", onBoxEnter);
      $(".box").on("mouseleave", onBoxLeave);
  });

  $(".carousel").on("mousemove", function(event) {
      if (mouseDownX !== 0) {
          mouseUpX = event.pageX;
      }
  });

  $(".carousel").on("mouseup", function(event) {
      if (mouseDownX !== 0) {
          handleSwipe();
      }
      mouseDownX = 0;
      mouseUpX = 0;
      startRotation();
  });

  function handleSwipe() {
      var swipeThreshold = 50;

      if (mouseUpX - mouseDownX > swipeThreshold) {
          // Swipe right
          rotate({ data: { d: "p" } });
      } else if (mouseDownX - mouseUpX > swipeThreshold) {
          // Swipe left
          rotate({ data: { d: "n" } });
      }
  }
  
  function rotate(e) {
      if (rotationInProgress) {
          return; 
      }

      rotationInProgress = true;

      if (e.data.d == "n") {
          currdeg = currdeg - 60;
      }
      if (e.data.d == "p") {
          currdeg = currdeg + 60;
      }
      carousel.css({
          "-webkit-transform": "rotateY(" + currdeg + "deg)",
          "-moz-transform": "rotateY(" + currdeg + "deg)",
          "-o-transform": "rotateY(" + currdeg + "deg)",
          "transform": "rotateY(" + currdeg + "deg)"
      });
      items.css({
          "-webkit-transform": "rotateY(" + (-currdeg) + "deg)",
          "-moz-transform": "rotateY(" + (-currdeg) + "deg)",
          "-o-transform": "rotateY(" + (-currdeg) + "deg)",
          "transform": "rotateY(" + (-currdeg) + "deg)"
      });

      setTimeout(function() {
          rotationInProgress = false;
      }, 2000); 
  }
  
  function startRotation() {
      if (intervalId === null) {
          intervalId = setInterval(function() {
              rotate({ data: { d: "n" } });
          }, 100); 
      }
  }
  
  function stopRotation() {
      if (intervalId !== null) {
          clearInterval(intervalId);
          intervalId = null;
      }
  }

  
  startRotation();

  document.addEventListener("visibilitychange", function() {
      if (document.visibilityState === "hidden") {
          stopRotation();
      } else if (document.visibilityState === "visible") {
          startRotation();
      }
  });

});

   var carousel = $(".carousel"),
        items = $(".item"),
        currdeg = 0,
        intervalId = null,
        rotationInProgress = false;

    var mouseDownX = 0;
    var mouseUpX = 0;

    $(".carousel").on("mousedown", function (event) {
      mouseDownX = event.pageX;
      stopRotation();
    });

    $(".carousel").on("mousemove", function (event) {
      if (mouseDownX !== 0) {
        mouseUpX = event.pageX;
      }
    });

    $(".carousel").on("mouseup", function (event) {
      if (mouseDownX !== 0) {
        handleSwipe();
      }
      mouseDownX = 0;
      mouseUpX = 0;
      startRotation();
    });

    function handleSwipe() {
      var swipeThreshold = 50; // Minimum swipe distance required to trigger a rotation

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
        return; // Ignora la richiesta di rotazione se già inizializzata
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
      }, 2000); // Imposta il tempo dopo il quale il carosello gira alla sequente slide
    }
    
    function startRotation() {
      if (intervalId === null) {
        intervalId = setInterval(function() {
          rotate({ data: { d: "n" } });
        }, 100); // Imposta il tempo dopo il quale il carosello gira alla sequente slide
      }
    }
    
    function stopRotation() {
      if (intervalId !== null) {
        clearInterval(intervalId);
        intervalId = null;
      }
    }

    // Inizia rotazione
    startRotation();

    // Detect visibility change and stop/start rotation accordingly
    document.addEventListener("visibilitychange", function() {
      if (document.visibilityState === "hidden") {
        stopRotation();
      } else if (document.visibilityState === "visible") {
        startRotation();
      }
    });
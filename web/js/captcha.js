// Función para generar un texto aleatorio para el captcha
    function generateRandomText(length) {
      const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
      let result = '';
      for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * characters.length));
      }
      return result;
    }

    // Función para dibujar el captcha en el canvas
    function drawCaptcha() {
      const canvas = document.getElementById('captchaCanvas');
      const ctx = canvas.getContext('2d');

      // Generar el texto aleatorio del captcha
      const captchaText = generateRandomText(6);

      // Dibujar el captcha en el canvas con líneas de ruido y colores aleatorios
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      ctx.font = '40px Arial'; // Ajustar el tamaño de la fuente según tus preferencias
      for (let i = 0; i < captchaText.length; i++) {
        const randomColor = '#' + Math.floor(Math.random()*16777215).toString(16); // Genera un color hexadecimal aleatorio
        ctx.fillStyle = randomColor;
        ctx.fillText(captchaText.charAt(i), 40 + i * 45, 70); // Ajustar la posición del texto
        // Agregar líneas de ruido al fondo del captcha
        for (let j = 0; j < 2; j++) {
          ctx.beginPath();
          ctx.moveTo(Math.random() * canvas.width, Math.random() * canvas.height);
          ctx.lineTo(Math.random() * canvas.width, Math.random() * canvas.height);
          ctx.strokeStyle = "#888888";
          ctx.stroke();
        }
      }

      // Almacenar el texto del captcha en una variable global para la validación
      window.captchaText = captchaText;
    }

    // Función para verificar si el texto ingresado coincide con el captcha generado
    function checkCaptcha() {
      const userInput = document.getElementById('captchaInput').value;
      if (userInput === window.captchaText) {
        alert('¡Captcha válido! Puedes continuar.');
        // Aquí puedes realizar la acción que deseas al superar el captcha
      } else {
        alert('Captcha incorrecto. Inténtalo de nuevo.');
        drawCaptcha();
      }
    }

    // Función para generar un nuevo captcha y dibujarlo
    function generateAndDrawCaptcha() {
      drawCaptcha();
    }

    // Dibujar el captcha cuando se carga la página por primera vez
    window.onload = drawCaptcha;
<template>
  <div class="kawaii-container">
    <h2 class="kawaii-title">🌸 To-Dos 🌸</h2>
    <ul class="kawaii-list">
      <li v-for="page in pages" :key="page.id" class="kawaii-item">
        <span class="flower-bullet">🌸</span>
        <div class="text-content">
          <strong class="page-title">{{ page.title }}</strong>
          <p class="page-desc">{{ page.content }}</p>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'PageList',
  data() {
    return {
      pages: [
        { id: 1, title: 'Einkaufsliste', content: 'Nutella Eis, Avocado, Wasser' },
        { id: 2, title: 'WebTech Projekt', content: 'Vue.js Frontend für MiniNotion bauen' },
        { id: 3, title: 'Tanzen', content: 'Tanzschuhe einstellen' }
      ]
    }
  }
}
</script>

<style scoped>
.kawaii-container {
  background-color: #AF8BD0 !important; /* Dein neuer Wunsch-Farbcode */
  border: none !important;
  border-radius: 0px !important;
  padding: 25px;
  max-width: 450px;
  margin: 30px auto;
  box-shadow: 0 8px 20px rgba(175, 139, 208, 0.3);
  font-family: 'Comic Sans MS', sans-serif;
}

.kawaii-title {
  color: #ffffff; /* Weißer Titel hebt sich super auf dem kräftigeren Lila ab */
  text-align: center;
  margin-bottom: 20px;
}

.kawaii-list {
  list-style-type: none;
  padding: 0;
}

.kawaii-item {
  background-color: #f3effa !important; /* Helles Kärtchen für optimalen Kontrast */
  border-radius: 0px !important;
  border: 1px solid #dcd0ef;
  padding: 12px 15px;
  margin-bottom: 12px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.flower-bullet {
  font-size: 1.2rem;
}

.text-content {
  display: flex;
  flex-direction: column;
}

.page-title {
  color: #4b0082;
  font-size: 1.05rem;
}

.page-desc {
  margin: 3px 0 0 0;
  color: #555555;
  font-size: 0.9rem;
}
</style>
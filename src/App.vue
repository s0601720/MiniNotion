<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'

// State für die Notizen vom Backend
const notes = ref<string[]>([])
const loading = ref(true)
const error = ref<string | null>(null)

// Die lokale Backend-URL für M3
const API_URL = 'https://mininotion-backend.onrender.com/api/notes'

const fetchNotes = async () => {
  try {
    const response = await fetch(API_URL)
    if (!response.ok) {
      throw new Error('Netzwerkantwort war nicht ok')
    }
    notes.value = await response.json()
  } catch (err: any) {
    error.value = 'Fehler beim Laden: ' + err.message
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchNotes()
})
</script>

<template>
  <header>
    <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="125" height="125" />

    <div class="wrapper">
      <HelloWorld msg="MiniNotion - M3 GET-Route" />

          <!-- Sektion für die Backend-Daten (M3) -->
          <div class="backend-notes">
            <h3>Notizen aus dem Backend:</h3>
            <p v-if="loading">Lade Notizen...</p>
            <p v-if="error" style="color: red;">{{ error }}</p>
            <ul v-if="!loading && !error">
              <li v-for="(note, index) in notes" :key="index">
                {{ note }}
              </li>
            </ul>
          </div>
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/about">About</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
  .backend-notes {
    margin-top: 1.5rem;
    padding: 1rem;
    background: rgba(255, 255, 255, 0.05);
    border-radius: 8px;
    border: 1px solid var(--color-border);
  }

  .backend-notes ul {
    padding-left: 1.2rem;
    margin-top: 0.5rem;
  }
}
</style>

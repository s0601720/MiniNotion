<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'

// Interface für eine Notiz (entspricht der Backend-Entity)
interface Page {
  id?: number
  title: string
  content: string
}

// State
const notes = ref<Page[]>([])
const loading = ref(true)
const error = ref<string | null>(null)

// Formular-State für neue Notizen
const newTitle = ref('')
const newContent = ref('')

const API_URL = import.meta.env.VITE_BACKEND_BASE_URL
  ? `${import.meta.env.VITE_BACKEND_BASE_URL}/api/notes`
  : '/api/notes'

// GET: Notizen laden
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

// POST: Neue Notiz an das Backend senden
const createNote = async () => {
  if (!newTitle.value.trim() && !newContent.value.trim()) return

  try {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        title: newTitle.value,
        content: newContent.value,
      }),
    })

    if (!response.ok) {
      throw new Error('Fehler beim Speichern der Notiz')
    }

    // Eingabefelder leeren
    newTitle.value = ''
    newContent.value = ''

    // Liste neu laden, damit die neue Notiz sofort angezeigt wird
    await fetchNotes()
  } catch (err: any) {
    error.value = 'Fehler beim Erstellen: ' + err.message
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
      <HelloWorld msg="MiniNotion - M4 Datenbank & POST" />

      <!-- Sektion für das Erstellungsformular (POST) -->
      <div class="backend-form">
        <h4>Neue Notiz hinzufügen:</h4>
        <form @submit.prevent="createNote">
          <input
            v-model="newTitle"
            placeholder="Titel eingeben..."
            class="input-field"
          />
          <textarea
            v-model="newContent"
            placeholder="Inhalt eingeben..."
            class="input-field"
          ></textarea>
          <button type="submit" class="submit-btn">Speichern</button>
        </form>
      </div>

      <!-- Sektion für die Backend-Daten (GET) -->
      <div class="backend-notes">
        <h3>Notizen aus der Datenbank:</h3>
        <p v-if="loading">Lade Notizen...</p>
        <p v-if="error" style="color: red;">{{ error }}</p>
        <ul v-if="!loading && !error">
          <li v-for="(note) in notes" :key="note.id">
            <strong>{{ note.title }}</strong>: {{ note.content }}
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

.backend-form, .backend-notes {
  margin-top: 1.5rem;
  padding: 1rem;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 8px;
  border: 1px solid var(--color-border);
  width: 100%;
}

.input-field {
  display: block;
  width: 100%;
  margin-bottom: 0.5rem;
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid var(--color-border);
  background: transparent;
  color: inherit;
}

.submit-btn {
  padding: 0.5rem 1rem;
  background: #42b883;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

.submit-btn:hover {
  background: #35495e;
}

.backend-notes ul {
  padding-left: 1.2rem;
  margin-top: 0.5rem;
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
}
</style>

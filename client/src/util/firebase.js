import firebase from "firebase/app";
import "firebase/firestore";

const config = {
  apiKey: process.env.REACT_APP_API_KEY,
  authDomain: process.env.REACT_APP_AUTH_DOMAIN,
  databaseURL: process.env.REACT_APP_DATABASE_URL,
  projectId: process.env.REACT_APP_PROJECT_ID,
  storageBucket: process.env.REACT_APP_STORAGE_BUCKET,
  messagingSenderId: process.env.REACT_APP_MESSAGING_SENDER_ID,
  appId: process.env.REACT_APP_APP_ID,
  measurementId: process.env.REACT_APP_MEASUREMENT_ID
};

class Firebase {
  constructor() {
    firebase.initializeApp(config);
    this.db = firebase.firestore(firebase);
  }

  getBuilds = () =>
    this.db
      .collection("builds")
      .get()
      .then(ss =>
        ss.docs.reduce((acc, curr) => {
          acc[curr.id] = { ...curr.data() };
          return acc;
        }, {})
      )
      .catch(err => console.error(err));
}

export default new Firebase();

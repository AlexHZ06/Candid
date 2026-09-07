import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import Index from './pages/index'
import SignIn from './pages/signin'
import PhotographerHome from './pages/photographerHome'
import { BrowserRouter, createBrowserRouter, RouterProvider } from 'react-router-dom'
import Gallery from './pages/gallery'
import Album from './pages/album'
import AlbumEdit from './pages/albumEdit'
import ImageSearch from './pages/imageSearch'
import SignUp from './pages/signup'
import AddImage from './pages/addimage'
import AlbumCreate from './pages/albumCreate'
import CHeader from './compnents/cheader'
import RejectedJwt from './pages/rejectedJwt'
import ClientHome from './pages/ClientHome'
import Profiles from './pages/profiles'
import CreateProfile from './pages/createProflie'

const router = createBrowserRouter([

  {path:"/", element: <Index/>},
  {path:"/signin", element: <SignIn/>},
  {path:"/phome", element:<PhotographerHome/>},
  {path:"/gallery", element:<Gallery/>},
  {path:"/album/:albumId/:albumName", element:<Album/>},
  {path:"/albumedit/:albumId/:albumName", element:<AlbumEdit/>},
  {path:"/imageSearch", element:<ImageSearch/>},
  {path:"/signup", element:<SignUp/>},
  {path:"/addimage/:albumId/:albumName", element:<AddImage/>},
  {path:"/rejectedjwt", element:<RejectedJwt/>},
  {path:"/albumcreate", element:<AlbumCreate/>},
  {path:"/chome", element:<ClientHome/>},
  {path:"/profiles", element:<Profiles/>},
  {path:"/createprofile", element:<CreateProfile/>}
  

])

createRoot(document.getElementById('root')).render(
  <RouterProvider router={router}/>
)

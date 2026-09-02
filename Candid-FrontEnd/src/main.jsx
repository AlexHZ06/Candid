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
import RejectedJwt from './pages/rejectedJwt'

const router = createBrowserRouter([

  {path:"/", element: <Index/>},
  {path:"/signin", element: <SignIn/>},
  {path:"/phome", element:<PhotographerHome/>},
  {path:"/gallery", element:<Gallery/>},
  {path:"/album/:albumId/:albumName", element:<Album/>},
  {path:"/albumedit/:albumId/:albumName", element:<AlbumEdit/>},
  {path:"/imageSearch", element:<ImageSearch/>},
  {path:"/signup", element:<SignUp/>},
  {path:"/addimage", element:<AddImage/>},
  {path:"/rejectedjwt", element:<RejectedJwt/>},
  {path:"/albumcreate", element:<AlbumCreate/>}

])

createRoot(document.getElementById('root')).render(
  <RouterProvider router={router}/>
)

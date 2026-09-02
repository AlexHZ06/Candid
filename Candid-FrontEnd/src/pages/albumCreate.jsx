import PHeader from "../compnents/pheader"
import { useRef, useEffect, useState, use } from "react"
import { jwtService } from "../logic/jwt"
import { Link } from "react-router-dom"
import { useNavigate } from "react-router-dom"


function AlbumCreate(){


    const [image, setImage] = useState(null)
    const [errorMessage, setErrorMessage] = useState("")
    const title = useRef();
    const [saving, setSaving] = useState(false)
    const description = useRef();

    const navigate = useNavigate()

    const tokenService = new jwtService()

    useEffect(() => {

        tokenService.checkTokenPhotographer()

    }, [])

    async function submit() {

        
        setErrorMessage("")

        if(title.current.value === "" && description.current.value === "") {
            setErrorMessage("Must enter details")
        }
        else if(title.current.value === "") {
            setErrorMessage("Must enter a title")
        }
        else if(description.current.value === "") {
            setErrorMessage("Must enter description")
        }
        else if(image === null) {
            setErrorMessage("Image must be uploaded")
        }
        else {
            const formData = new FormData()
            formData.append("file", image)
            formData.append("name", title.current.value)
            formData.append("description", description.current.value)
            setSaving(true)
            fetch("/api/album/photographer/createalbum", {
                method:"POST",
                headers:{ auth:localStorage.getItem("jwt") },
                body:formData
            }).then(response => response.json()).then(async data => {

                if(data.sucsess) {
                    navigate("/gallery")
                }
                else {
                    setSaving(false)
                    if(data.internalCode === 401) {

                        const result = await tokenService.requestJwt()

                        if(result) {
                            
                            fetch("/api/album/photographer/createalbum", {
                                method:"POST",
                                headers:{ auth:localStorage.getItem("jwt") },
                                body:formData
                            }).then(res => res.json()).then(d => {

                                if(d.sucsess) {
                                    navigate("/gallery")
                                }
                                else {
                                    setErrorMessage(d.error)
                                }

                            })

                        }
                        else {
                            return
                        }

                    }
                    else {
                        setErrorMessage(data.error)
                    }
                }

            })
        }
    }

    function uploadImage(image){

        setErrorMessage("")

        if (!image || !image.type.startsWith("image/")){

            setImage(null)
            setErrorMessage("not valid file type")

        }
        else{

            setImage(image)

        }

    }

    return(

        <div className="
        
            w-screen 
            min-h-screen 
        
        ">
            <PHeader active="Gallery"/>
            <div className="
            
                w-full
                h-full
                flex
                justify-center
                mt-20
            
            ">
                <div className="
                
                    border-2
                    border-neutral-300
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    w-[100vh]
                    h-[70vh]
                    rounded-xl
                    flex
                    flex-col
                    items-center
               
                
                ">
                    <div className="
                    
                        flex
                        flex-row
                        w-[80vh]
                        justify-between
                        mt-10
                    
                    ">
                        <input ref={title} placeholder="Title" type="text" className="
                        
                            border-2
                      
                            h-10
                            rounded-md
                            w-6/10
                            pl-2
                            border-neutral-500
                            hover:border-black

                            transition
                            duration-200
                          

                        
                        "/>
                        <div className="
                        
                            w-3/10
                            border-2
                       
                     
                            rounded-md
                            pl-2
                            border-neutral-500

                        
                        ">

                            

                        </div>
                    </div>
                    <textarea ref={description} placeholder="Desctiption" className="
                    
                        w-[80vh]
                        border-2
                        rounded-md
                         border-neutral-500
                         hover:border-black
                        transition
                        duration-200
                        mt-5
                        h-[6vh]
                        pl-2
                        resize-none

                    
                    "/>
                    <label
                    className="
                        flex flex-col
                        items-center
                        justify-center
                        w-[80vh]
                        h-[40vh]
                        mt-5
                        border-2
                        rounded-md
                        border-neutral-500
                        hover:border-black
                        transition
                        duration-200
                        cursor-pointer
                        text-neutral-600
                        hover:text-black
                        active:text-neutral-600
                    "
                    >
                    <div className={`
                    
                        flex
                        flex-col
                        items-center
                        ${image === null ? "": "hidden"}
                        
                    
                    `}>
                        <div>
                            <img src="src/resources/Image--Streamline-Rounded-Streamline-Material-Free.svg" className="
                            
                                
        
                            "/>
                        </div>

                        <p className="mt-2 text-gray-600">
                            Click to upload an image
                        </p>
                    </div>
                    <div className={`
                    
                        w-[80vh]
                        h-[40vh]
                        p-30
                        flex
                        justify-center
                        items-center
                        flex-col
                        ${image === null ? "hidden" : ""}
                    
                    `}>
                        <img src={image ? URL.createObjectURL(image) : ""} className="
                        
                            h-[30vh]
                        
                        "/>
                        <p>Click to change image</p>
                    </div>

                    <input disabled={saving} onChange={(e) => uploadImage(e.target.files[0])} type="file" accept="image/*" className="hidden"/>
                    </label>
                    <p className="
                    
                        text-red-600
                    
                    ">{errorMessage}</p>
                    <p className={`
                    
                        ${saving === true ? "": "hidden"}
                    
                    `}>Saving ...</p>
                    <div className={`
                    
                        flex
                        flex-row
                        w-[80vh]
                        items-center
                        justify-center
                        gap-10
                        mt-7
                        ${saving === false ? "": "hidden"}
                    
                    `}>
                        <button onClick={submit} className="
                        
                            bg-neutral-700
                            text-white
                            rounded-xl
                            w-[10vh]
                            h-8.75
                            flex
                            items-center
                            justify-center
                             hover:bg-gray-900
      
                            transition
                            duration-100

                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                            active:bg-white
                            active:text-black

                        
                        ">Submit</button>
                        <Link to="/gallery" className="
                        
                            bg-red-500
                            text-white
                            rounded-xl
                            w-[10vh]
                            h-8.75
                            flex
                            items-center
                            justify-center
                             hover:bg-red-700
      
                            transition
                            duration-100

                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                            active:bg-white
                            active:text-black
                        
                        ">Back</Link>
                    </div>

                </div>
            </div>
        </div>

    )

}

export default AlbumCreate
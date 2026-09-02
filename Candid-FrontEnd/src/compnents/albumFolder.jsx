import { Link } from "react-router-dom"

function AlbumFolder({title, photos, id, thumbnail}){

    return(

        <Link to={`/album/${id}/${title}`} className="
        
            bg-neutral-200
            w-[200px]
            h-[200px]
            rounded-xl
            shadow-[0_0px_10px_rgba(0,0,0,0.25)]
            pl-2
            pt-2

            hover:shadow-[0_0px_10px_rgba(0,0,0,0.5)]
            transition
            duration-100

            active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]
            relative

        ">

            <img src={thumbnail} className="
            
               absolute
               w-[200px]
               rounded-xl
               top-0
               left-0
            
            "/>
            <p className="
            
                absolute
                text-2xl
                text-white
            
            ">{title}</p>
        </Link>

    )

}

export default AlbumFolder